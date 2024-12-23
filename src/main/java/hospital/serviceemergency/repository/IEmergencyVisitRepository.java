package hospital.serviceemergency.repository;

import hospital.serviceemergency.model.EmergencyVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmergencyVisitRepository extends JpaRepository<EmergencyVisit, Long> {

}
