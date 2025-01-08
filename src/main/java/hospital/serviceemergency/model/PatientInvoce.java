package hospital.serviceemergency.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import hospital.serviceemergency.model.enums.EPaymentStatus;
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
@Table(name = "patient_invoices")
public class PatientInvoce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false)
    private Long id;

    @Column(name = "total_amount", nullable = false)
    private Float totalAmount;

    @Column(name = "invoice_timestamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime invoiceTimestamp;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private EPaymentStatus paymentStatus;

    @Column(name = "payment_received")
    private Boolean paymentReceived;

    @Column(name = "payment_received_timestamp")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentReceivedTimestamp;

    // many to one visit_id
    @ManyToOne
    @JoinColumn(name = "visit_id", nullable = false)
    private EmergencyVisit emergencyVisit;

    // many to one created_by_staff_id
    @ManyToOne
    @JoinColumn(name = "created_by_staff_id", nullable = false)
    private Staff createdByStaff;

}
