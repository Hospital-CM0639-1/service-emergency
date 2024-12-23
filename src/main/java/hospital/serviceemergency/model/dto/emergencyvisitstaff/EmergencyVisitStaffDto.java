package hospital.serviceemergency.model.dto.emergencyvisitstaff;

import com.fasterxml.jackson.annotation.JsonInclude;
import hospital.serviceemergency.model.enums.EStaffRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmergencyVisitStaffDto {

    private EStaffRole staffRole;
    private Long visitId;
    private Long staffId;
    private LocalDateTime assignedAt;
    private EmergencyVisitDto emergencyVisit;

}

@Getter
@Setter
class EmergencyVisitDto {
    private Long id;
    private PatientDto patient;
}

@Getter
@Setter
class PatientDto {
    private Long id;
}