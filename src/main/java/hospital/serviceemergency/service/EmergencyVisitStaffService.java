package hospital.serviceemergency.service;

import hospital.serviceemergency.model.EmergencyVisitStaff;
import hospital.serviceemergency.model.dto.emergencyvisitstaff.EmergencyVisitStaffDto;
import hospital.serviceemergency.repository.IEmergencyVisitStaffRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmergencyVisitStaffService {

    private final IEmergencyVisitStaffRepository emergencyVisitStaffRepository;
    private final ModelMapper modelMapper;

    public EmergencyVisitStaffService(IEmergencyVisitStaffRepository emergencyVisitStaffRepository, ModelMapper modelMapper) {
        this.emergencyVisitStaffRepository = emergencyVisitStaffRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
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

    // Convert to DTO
    private EmergencyVisitStaffDto convertToDto(EmergencyVisitStaff emergencyVisitStaff) {
        return modelMapper.map(emergencyVisitStaff, EmergencyVisitStaffDto.class);
    }


}
