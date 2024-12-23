package hospital.serviceemergency.model.dto.patientvital;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DetailPatientVitalDto extends PatientVitalDto {
    private StaffDto staff;
    private EmergencyVisitDto emergencyVisit;
}

@Getter
@Setter
class StaffDto {
    private Long id;
}

@Getter
@Setter
class EmergencyVisitDto {
    private Long id;
}