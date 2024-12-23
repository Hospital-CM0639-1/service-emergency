package hospital.serviceemergency.repository;

import hospital.serviceemergency.model.PatientInvoce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientInvoiceRepository extends JpaRepository<PatientInvoce, Long> {

}
