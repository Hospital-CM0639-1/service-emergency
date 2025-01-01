package hospital.serviceemergency.repository;

import hospital.serviceemergency.model.EmergencyVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmergencyVisitRepository extends JpaRepository<EmergencyVisit, Long> {
    // find emergency visit by patient id
    EmergencyVisit findByPatientId(Long patientId);
}
