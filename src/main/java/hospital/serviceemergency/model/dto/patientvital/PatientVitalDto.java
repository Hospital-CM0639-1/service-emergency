package hospital.serviceemergency.model.dto.patientvital;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PatientVitalDto {
    private Long id;
    private LocalDateTime recordedAt;
    private Float bodyTemperature;
    private Integer bloodPressureSystolic;
    private Integer bloodPressureDiastolic;
    private Integer heartRate;
    private Integer respiratoryRate;
    private Float oxygenSaturation;
    private String additionalObservations;
}
