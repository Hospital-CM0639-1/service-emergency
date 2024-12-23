package hospital.serviceemergency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import hospital.serviceemergency.model.enums.EStaffRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(EmergencyVisitStaffId.class)
@Table(name = "emergency_visit_staff")
public class EmergencyVisitStaff {

    @Id
    @Column(name = "staff_role", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private EStaffRole staffRole;

    @Id
    @Column(name = "visit_id", nullable = false)
    private Long visitId;

    @Id
    @Column(name = "staff_id", nullable = false)
    private Long staffId;

    @Column(name = "assigned_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assignedAt;

    @ManyToOne
    @JoinColumn(name = "visit_id", insertable = false, updatable = false)
    private EmergencyVisit emergencyVisit;

    @ManyToOne
    @JoinColumn(name = "staff_id", insertable = false, updatable = false)
    private Staff staff;

}
