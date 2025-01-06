package hospital.servicedoctor.model;

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
@Table(name = "medical_procedures")
public class MedicalProcedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procedure_id", nullable = false)
    private Long id;

    @Column(name = "procedure_name", nullable = false)
    private String procedureName;

    @Column(name = "procedure_timestamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime procedureTimestamp;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "procedure_cost")
    private Float procedureCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by_staff_id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "visit_id")
    private EmergencyVisit emergencyVisit;
}
