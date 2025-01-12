package hospital.serviceemergency.service;
import hospital.serviceemergency.model.EmergencyVisit;
import hospital.serviceemergency.model.HospitalBed;
import hospital.serviceemergency.model.dto.hospitalbed.DetailHospitalBedDto;
import hospital.serviceemergency.model.dto.hospitalbed.HospitalBedDto;
import hospital.serviceemergency.model.dto.hospitalbed.PatientBedAssignmentDto;
import hospital.serviceemergency.model.enums.ECurrentBedStatus;
import hospital.serviceemergency.model.enums.EPatientStatus;
import hospital.serviceemergency.model.enums.EWardSection;
import hospital.serviceemergency.repository.IEmergencyVisitRepository;
import hospital.serviceemergency.repository.IHospitalBedRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class HospitalBedService {
    private static final Logger logger = LoggerFactory.getLogger(HospitalBedService.class);

    private final IHospitalBedRepository hospitalBedRepository;
    private final IEmergencyVisitRepository emergencyVisitRepository;
    private final ModelMapper modelMapper;

    public HospitalBedService(IHospitalBedRepository hospitalBedRepository,
                              IEmergencyVisitRepository emergencyVisitRepository,
                              ModelMapper modelMapper) {
        this.hospitalBedRepository = hospitalBedRepository;
        this.emergencyVisitRepository = emergencyVisitRepository;
        this.modelMapper = modelMapper;
    }

    public Page<HospitalBedDto> getAllPageableHospitalBeds(Pageable pageable) {
        return hospitalBedRepository.findAll(pageable).
                map(this::convertToDto);
    }

    public DetailHospitalBedDto getHospitalBedById(Long hospitalBedId) {
        return hospitalBedRepository.findById(hospitalBedId).
                map(this::convertToDetailedDto).orElse(null);
    }

    public DetailHospitalBedDto getHospitalBedByPatientId(Long patientId) {
        return hospitalBedRepository.findByEmergencyVisit_Patient_IdAndEmergencyVisitIsNotNull(patientId)
                .map(this::convertToDetailedDto).orElseThrow(() -> {
                    logger.error("Hospital Bed with patient id: {} not found", patientId);
                    return new IllegalArgumentException("Hospital Bed with patient id: " + patientId + " not found");
                });
    }

    // countByCurrentStatusAndWardSection
    public List<Map<String, Integer>> countByCurrentStatusAndWardSection() {
        return hospitalBedRepository.countByCurrentStatusAndWardSection(ECurrentBedStatus.AVAILABLE);
    }

    public DetailHospitalBedDto getHospitalBedByBedNumber(String bedNumber) {
        return hospitalBedRepository.findByBedNumber(bedNumber).
                map(this::convertToDetailedDto).orElse(null);
    }

    public List<HospitalBedDto> getHospitalBedsByCurrentStatusAndWardSection(ECurrentBedStatus currentStatus, EWardSection wardSection) {
        return hospitalBedRepository.findByCurrentStatusAndWardSection(currentStatus, wardSection).
                stream().map(this::convertToDto).toList();
    }

    public DetailHospitalBedDto saveHospitalBed(HospitalBedDto hospitalBedDto) {
        HospitalBed hospitalBed = convertToEntity(hospitalBedDto);
        HospitalBed savedHospitalBed = hospitalBedRepository.save(hospitalBed);
        return convertToDetailedDto(savedHospitalBed);
    }

    public DetailHospitalBedDto updateHospitalBed(HospitalBedDto hospitalBedDto, Long id) {
        boolean existsById = hospitalBedRepository.existsById(id);
        if (!existsById) {
            logger.error("Hospital Bed with id: {} not found", id);
            throw new IllegalArgumentException("Hospital Bed with id: " + id + " not found");
        }
        HospitalBed hospitalBed = convertToEntity(hospitalBedDto);
        hospitalBed.setId(id);
        HospitalBed savedHospitalBed = hospitalBedRepository.save(hospitalBed);
        return convertToDetailedDto(savedHospitalBed);
    }

    @Transactional
    public DetailHospitalBedDto assignPatientToHospitalBed(Long patientId, Long hospitalBedId, ECurrentBedStatus currentStatus) {
        HospitalBed hospitalBed = hospitalBedRepository.findById(hospitalBedId).orElseThrow(() -> {
            logger.error("Hospital Bed with id: {} not found", hospitalBedId);
            return new IllegalArgumentException("Hospital Bed with id: " + hospitalBedId + " not found");
        });
        // find emergency visit by patient id
        List<EmergencyVisit> emergencyVisitList = emergencyVisitRepository.findByPatientId(patientId);
        // get emergency visit with status IN_TREATMENT
        EmergencyVisit emergencyVisit = emergencyVisitList.stream()
                .filter(visit -> visit.getPatientStatus() == EPatientStatus.IN_TREATMENT)
                .findFirst().orElse(null);
        if (emergencyVisit == null) {
            logger.error("Emergency Visit with patient id: {} not found", patientId);
            throw new IllegalArgumentException("Emergency Visit with patient id: " + patientId + " not found");
        }
        if (hospitalBed.getCurrentStatus() != ECurrentBedStatus.AVAILABLE) {
            logger.error("Hospital Bed with id: {} is not available", hospitalBedId);
            throw new IllegalArgumentException("Hospital Bed with id: " + hospitalBedId + " is not available");
        }
        if (emergencyVisit.getPatientStatus() != EPatientStatus.IN_TREATMENT) {
            logger.error("Patient with id: {} is not in treatment", patientId);
            throw new IllegalArgumentException("Patient with id: " + patientId + " is not in treatment");
        }

        hospitalBed.setEmergencyVisit(emergencyVisit);
        hospitalBed.setCurrentStatus(currentStatus);
        HospitalBed savedHospitalBed = hospitalBedRepository.save(hospitalBed);
        emergencyVisit.setPatientStatus(EPatientStatus.ADMITTED);
        emergencyVisitRepository.save(emergencyVisit);
        return convertToDetailedDto(savedHospitalBed);
    }

    @Transactional
    public DetailHospitalBedDto freeBedByPatientId(Long patientId) {
        // Find active visit for patient
        EmergencyVisit visit = emergencyVisitRepository.findActiveVisitByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No active visit found for patient ID: " + patientId));

        // Find bed assigned to this visit
        HospitalBed bed = hospitalBedRepository.findByEmergencyVisit_Id(visit.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No bed found for patient ID: " + patientId));

        // Update visit status to discharged
        visit.setPatientStatus(EPatientStatus.DISCHARGED);
        visit.setDischargeTimestamp(LocalDateTime.now());
        emergencyVisitRepository.save(visit);

        // Free the bed
        bed.setCurrentStatus(ECurrentBedStatus.AVAILABLE);
        bed.setEmergencyVisit(null);
        HospitalBed hospitalBed = hospitalBedRepository.save(bed);

        return convertToDetailedDto(hospitalBed);
    }

    public List<PatientBedAssignmentDto> getPatientsNeedingBeds() {
        return emergencyVisitRepository.findPatientsNeedingBeds();
    }


    public void deleteHospitalBed(Long id) {
        boolean existsById = hospitalBedRepository.existsById(id);
        if (!existsById) {
            logger.error("Hospital Bed with id: {} not found", id);
            throw new IllegalArgumentException("Hospital Bed with id: " + id + " not found");
        }
        hospitalBedRepository.deleteById(id);
    }



    // Converters
    private HospitalBedDto convertToDto(HospitalBed hospitalBed) {
        return modelMapper.map(hospitalBed, HospitalBedDto.class);
    }
    private DetailHospitalBedDto convertToDetailedDto(HospitalBed hospitalBed) {
        return modelMapper.map(hospitalBed, DetailHospitalBedDto.class);
    }
    private HospitalBed convertToEntity(HospitalBedDto hospitalBedDto) {
        return modelMapper.map(hospitalBedDto, HospitalBed.class);
    }


}
