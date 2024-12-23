package hospital.serviceemergency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "patient_vitals")
public class PatientVital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vital_record_id", nullable = false)
    private Long id;

    @Column(name = "recorded_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recordedAt;

    @Column(name = "body_temperature")
    private Float bodyTemperature;

    @Column(name = "blood_pressure_systolic")
    private Integer bloodPressureSystolic;

    @Column(name = "blood_pressure_diastolic")
    private Integer bloodPressureDiastolic;

    @Column(name = "heart_rate")
    private Integer heartRate;

    @Column(name = "respiratory_rate")
    private Integer respiratoryRate;

    @Column(name = "oxygen_saturation")
    private Float oxygenSaturation;

    @Column(name = "additional_observations", columnDefinition = "TEXT")
    private String additionalObservations;

    @ManyToOne
    @JoinColumn(name = "visit_id")
    private EmergencyVisit emergencyVisit;

    @ManyToOne
    @JoinColumn(name = "recorded_by_staff_id")
    private Staff staff;
}
