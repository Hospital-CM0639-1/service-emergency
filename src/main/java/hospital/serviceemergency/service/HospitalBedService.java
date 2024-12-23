package hospital.serviceemergency.service;
import hospital.serviceemergency.model.HospitalBed;
import hospital.serviceemergency.model.dto.hospitalbed.DetailHospitalBedDto;
import hospital.serviceemergency.model.dto.hospitalbed.HospitalBedDto;
import hospital.serviceemergency.model.enums.ECurrentBedStatus;
import hospital.serviceemergency.repository.IHospitalBedRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalBedService {
    private static final Logger logger = LoggerFactory.getLogger(HospitalBedService.class);

    private final IHospitalBedRepository hospitalBedRepository;
    private final ModelMapper modelMapper;

    public HospitalBedService(IHospitalBedRepository hospitalBedRepository, ModelMapper modelMapper) {
        this.hospitalBedRepository = hospitalBedRepository;
        this.modelMapper = modelMapper;
    }

    public Page<HospitalBedDto> getAllPageableHospitalBeds(Pageable pageable) {
        return hospitalBedRepository.findAll(pageable).
                map(this::convertToDto);
    }

    public HospitalBedDto getHospitalBedById(Long hospitalBedId) {
        return hospitalBedRepository.findById(hospitalBedId).
                map(this::convertToDto).orElse(null);
    }

    public DetailHospitalBedDto getHospitalBedByPatientId(Long patientId) {
        return hospitalBedRepository.findByEmergencyVisit_Patient_IdAndEmergencyVisitIsNotNull(patientId)
                .map(this::convertToDetailedDto).orElseThrow(() -> {
                    logger.error("Hospital Bed with patient id: {} not found", patientId);
                    return new IllegalArgumentException("Hospital Bed with patient id: " + patientId + " not found");
                });
    }

    public DetailHospitalBedDto getHospitalBedByBedNumber(String bedNumber) {
        return hospitalBedRepository.findByBedNumber(bedNumber).
                map(this::convertToDetailedDto).orElse(null);
    }

    public List<HospitalBedDto> getHospitalBedsByCurrentStatusAndWardSection(ECurrentBedStatus currentStatus, String wardSection) {
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
