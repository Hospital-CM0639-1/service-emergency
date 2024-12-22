package hospital.serviceemergency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import hospital.serviceemergency.model.enums.EPatientStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "emergency_visits")
public class EmergencyVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id", nullable = false)
    private Long id;

    @Column(name = "admission_timestamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime admissionTimestamp;

    @Column(name = "discharge_timestamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dischargeTimestamp;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "current_status", nullable = false)
    private EPatientStatus patientStatus;

    @Column(name = "triage_notes", columnDefinition = "TEXT")
    private String triageNotes;

    @Column(name = "priority_level")
    private String priorityLevel;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(mappedBy = "emergencyVisit")
    List<HospitalBed> hospitalBeds;

}
