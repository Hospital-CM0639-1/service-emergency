package hospital.serviceemergency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import hospital.serviceemergency.model.enums.EStaffRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "role", nullable = false)
    private EStaffRole role;

    @Column(name = "department")
    private String department;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "hire_date", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hireDate;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private boolean isActive;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientVital> patientVitals = new ArrayList<>();

    @OneToMany(mappedBy = "createdByStaff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientInvoce> patientInvoices = new ArrayList<>();


}
