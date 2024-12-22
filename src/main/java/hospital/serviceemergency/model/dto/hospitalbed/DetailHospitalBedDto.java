package hospital.serviceemergency.model.dto.hospitalbed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailHospitalBedDto extends HospitalBedDto {
    private EmergencyVisitDto emergencyVisit;
}

@Getter
@Setter
class EmergencyVisitDto {
    private String triageNotes;
    private PatientDto patient;
}

@Getter
@Setter
class PatientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String emergencyContactName;
    private String emergencyContactNumber;
}

