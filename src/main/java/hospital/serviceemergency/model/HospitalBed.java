package hospital.serviceemergency.model;

import hospital.serviceemergency.model.enums.ECurrentBedStatus;
import hospital.serviceemergency.model.enums.EWardSection;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hospital_beds")
public class HospitalBed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bed_id", nullable = false)
    private Long id;

    @Column(name = "bed_number", nullable = false)
    private String bedNumber;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "ward_section", nullable = false)
    private EWardSection wardSection;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "current_status", nullable = false)
    private ECurrentBedStatus currentStatus;

    @ManyToOne
    @JoinColumn(name = "current_visit_id")
    private EmergencyVisit emergencyVisit;

}
