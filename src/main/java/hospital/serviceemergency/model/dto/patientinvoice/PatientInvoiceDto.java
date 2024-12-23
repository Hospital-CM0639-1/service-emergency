package hospital.serviceemergency.model.dto.patientinvoice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PatientInvoiceDto {
    private Long id;
    private Float totalAmount;
    private String paymentStatus;
    private LocalDateTime invoiceTimestamp;
    private LocalDateTime paymentReceivedTimestamp;
    private Float paymentReceivedAmount;
    private EmergencyVisitDto emergencyVisit;
    private StaffDto createdByStaff;
}

@Getter
@Setter
class EmergencyVisitDto {
    private Long id;
}

@Getter
@Setter
class StaffDto {
    private Long id;
}