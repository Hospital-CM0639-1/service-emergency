package hospital.serviceemergency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import hospital.serviceemergency.model.enums.ECurrentBedStatus;
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
@Table(name = "hospital_beds")
public class HospitalBed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bed_id", nullable = false)
    private Long id;

    @Column(name = "bed_number", nullable = false)
    private String bedNumber;

    @Column(name = "ward_section", nullable = false)
    private String wardSection;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "current_status", nullable = false)
    private ECurrentBedStatus currentStatus;

    @Column(name = "last_cleaned_timestamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastCleanedTimestamp;

    @ManyToOne
    @JoinColumn(name = "current_visit_id")
    private EmergencyVisit emergencyVisit;

}
