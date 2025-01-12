package hospital.serviceemergency.model.dto.patientinvoice;

import hospital.serviceemergency.model.enums.EPaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PatientInvoiceDto {
    private Long id;
    private Float totalAmount;
    private LocalDateTime invoiceTimestamp;
    private EPaymentStatus paymentStatus;
    private LocalDateTime paymentReceivedTimestamp;
    private Boolean paymentReceived;
    private EmergencyVisitDto emergencyVisit;
    private StaffDto createdByStaff;
}

@Getter
@Setter
class EmergencyVisitDto {
    private Long id;
    private PatientDto patient;
}

@Getter
@Setter
class PatientDto {
    private Long id;
    private String firstName;
    private String lastName;
}

@Getter
@Setter
class StaffDto {
    private Long id;
}
