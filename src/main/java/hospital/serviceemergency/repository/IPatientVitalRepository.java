package hospital.serviceemergency.repository;

import hospital.serviceemergency.model.PatientVital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface IPatientVitalRepository extends JpaRepository<PatientVital, Long>  {

    List<PatientVital> findAllByStaff_IdAndRecordedAtBetween(Long id, LocalDateTime startDate, LocalDateTime finishDate);

    List<PatientVital> findAllByEmergencyVisit_Patient_IdAndRecordedAtBetween(Long patientId, LocalDateTime startDate, LocalDateTime finishDate);


}
