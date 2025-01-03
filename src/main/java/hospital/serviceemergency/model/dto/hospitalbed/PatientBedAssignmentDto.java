package hospital.serviceemergency.model.dto.hospitalbed;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PatientBedAssignmentDto {
    private Long visitId;
    private Long patientId;
    private String firstName;
    private String lastName;
    private LocalDateTime admissionTimestamp;
    private String priorityLevel;
}