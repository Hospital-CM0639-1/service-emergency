package hospital.serviceemergency.repository;

import hospital.serviceemergency.model.MedicalProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMedicalProcedureRepository extends JpaRepository<MedicalProcedure, Long> {

    // get all by visit id
    List<MedicalProcedure> findByEmergencyVisit_Id(Long visitId);

}
