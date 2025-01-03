package hospital.serviceemergency.repository;

import hospital.serviceemergency.model.HospitalBed;
import hospital.serviceemergency.model.enums.ECurrentBedStatus;
import hospital.serviceemergency.model.enums.EWardSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IHospitalBedRepository extends JpaRepository<HospitalBed, Long> {

    @Query("SELECT hb FROM HospitalBed hb " +
            "JOIN hb.emergencyVisit ev " +
            "WHERE ev.patient.id = :patientId " +
            "AND hb.emergencyVisit IS NOT NULL")
    List<HospitalBed> findByPatientId(@Param("patientId") Long patientId);

    Optional<HospitalBed> findByEmergencyVisit_Patient_IdAndEmergencyVisitIsNotNull(Long patientId);

    Optional<HospitalBed> findByEmergencyVisit_Id(Long visitId);

    Optional<HospitalBed> findByBedNumber(String bedNumber);

    List<HospitalBed> findByCurrentStatusAndWardSection(ECurrentBedStatus currentStatus, EWardSection wardSection);
}
