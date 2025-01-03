package hospital.serviceemergency.service;

import hospital.serviceemergency.model.EmergencyVisitStaff;
import hospital.serviceemergency.model.dto.emergencyvisitstaff.EmergencyVisitStaffDto;
import hospital.serviceemergency.model.enums.EPatientStatus;
import hospital.serviceemergency.repository.IEmergencyVisitStaffRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmergencyVisitStaffService {
    private static final Logger logger = LoggerFactory.getLogger(EmergencyVisitStaffService.class);
    private final IEmergencyVisitStaffRepository emergencyVisitStaffRepository;
    private final ModelMapper modelMapper;

    public EmergencyVisitStaffService(IEmergencyVisitStaffRepository emergencyVisitStaffRepository, ModelMapper modelMapper) {
        this.emergencyVisitStaffRepository = emergencyVisitStaffRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // Get all pageable emergency visit staff by patient status
    public Page<EmergencyVisitStaffDto> getAllEmergencyVisitStaff(Pageable pageable, EPatientStatus currentStatus) {
        if (currentStatus != null) {
            return emergencyVisitStaffRepository.findAllByEmergencyVisit_PatientStatus(currentStatus, pageable)
                    .map(this::convertToDto);
        }
        return emergencyVisitStaffRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    // Get all emergency visits between dates
    public List<EmergencyVisitStaffDto> getEmergencyVisitStaffBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return emergencyVisitStaffRepository.findEmergencyVisitStaffBetweenDates(startDate, endDate)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }



    // Get all patient assigned to a staff
    public List<EmergencyVisitStaffDto> getPatientsAssignedToStaff(Long staffId) {
        return this.emergencyVisitStaffRepository.findPatientsAssignedToStaff(staffId)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Get all staff assigned to a patient
    public List<EmergencyVisitStaffDto> getStaffAssignedToPatient(Long patientId) {
        return this.emergencyVisitStaffRepository.findStaffAssignedToPatient(patientId)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Get all staff and patients involved in a visit
    public List<EmergencyVisitStaffDto> getPatientAndStaffInvolvedInVisit(Long visitId) {
        return emergencyVisitStaffRepository.findPatientAndStaffInvolvedInVisit(visitId)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Add emergency visit staff
    public EmergencyVisitStaffDto addEmergencyVisitStaff(EmergencyVisitStaffDto emergencyVisitStaffDto) {
        EmergencyVisitStaff emergencyVisitStaff = modelMapper.map(emergencyVisitStaffDto, EmergencyVisitStaff.class);
        emergencyVisitStaff = emergencyVisitStaffRepository.save(emergencyVisitStaff);
        return convertToDto(emergencyVisitStaff);
    }

    // Update emergency visit staff
    public EmergencyVisitStaffDto updateEmergencyVisitStaff(EmergencyVisitStaffDto emergencyVisitStaffDto, Long visitId, Long staffId) {
        boolean exists = emergencyVisitStaffRepository.existsById(visitId, staffId);
        if (!exists) {
            logger.error("Emergency visit staff with visit id {} and staff id {} does not exist", visitId, staffId);
            throw new IllegalArgumentException("Emergency visit staff does not exist with visit id " + visitId + " and staff id " + staffId);
        }
        EmergencyVisitStaff emergencyVisitStaff = modelMapper.map(emergencyVisitStaffDto, EmergencyVisitStaff.class);
        emergencyVisitStaff = emergencyVisitStaffRepository.save(emergencyVisitStaff);
        return convertToDto(emergencyVisitStaff);
    }

    // Delete emergency visit staff
    @Transactional
    public void deleteEmergencyVisitStaff(Long visitId, Long staffId) {
        boolean exists = emergencyVisitStaffRepository.existsById(visitId, staffId);
        if (!exists) {
            logger.error("Emergency visit staff with visit id {} and staff id {} does not exist", visitId, staffId);
            throw new IllegalArgumentException("Emergency visit staff does not exist with visit id " + visitId + " and staff id " + staffId);
        }
        emergencyVisitStaffRepository.deleteByVisitIdAndStaffId(visitId, staffId);
    }


    // Convert to DTO
    private EmergencyVisitStaffDto convertToDto(EmergencyVisitStaff emergencyVisitStaff) {
        return modelMapper.map(emergencyVisitStaff, EmergencyVisitStaffDto.class);
    }


}
