package hospital.serviceemergency.model.dto.emergencyvisit;

import hospital.serviceemergency.model.enums.EPatientStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Data
public class EmergencyVisitDto {
    private Long id;
    private LocalDateTime admissionTimestamp;
    private LocalDateTime dischargeTimestamp;
    private EPatientStatus patientStatus;
    private String triageNotes;
    private String priorityLevel;
    private PatientDto patient;
}

@Getter
@Setter
class PatientDto {
    private Long id;
    private String firstName;
    private String lastName;
}
