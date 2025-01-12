package hospital.serviceemergency.repository;

import hospital.serviceemergency.model.PatientInvoce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPatientInvoiceRepository extends JpaRepository<PatientInvoce, Long> {

    // Get invoice by patient id
    List<PatientInvoce> findByEmergencyVisit_Patient_Id(Long patientId);

    // Get invoice by visit id
    PatientInvoce findByEmergencyVisit_Id(Long visitId);

}
