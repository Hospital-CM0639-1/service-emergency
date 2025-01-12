package hospital.serviceemergency.repository;

import hospital.serviceemergency.model.EmergencyVisit;
import hospital.serviceemergency.model.dto.hospitalbed.PatientBedAssignmentDto;
import hospital.serviceemergency.model.enums.EPatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEmergencyVisitRepository extends JpaRepository<EmergencyVisit, Long> {
    // find emergency visit by patient id
    List<EmergencyVisit> findByPatientId(Long patientId);

    @Query("SELECT new hospital.serviceemergency.model.dto.hospitalbed.PatientBedAssignmentDto(" +
            "ev.id, p.id, p.firstName, p.lastName, ev.admissionTimestamp, ev.priorityLevel) " +
            "FROM EmergencyVisit ev " +
            "JOIN ev.patient p " +
            "WHERE ev.patientStatus = 'IN_TREATMENT' " +
            "AND NOT EXISTS (SELECT 1 FROM HospitalBed hb WHERE hb.emergencyVisit = ev) " +
            "ORDER BY ev.priorityLevel, ev.admissionTimestamp")
    List<PatientBedAssignmentDto> findPatientsNeedingBeds();

    @Query("SELECT ev FROM EmergencyVisit ev " +
            "WHERE ev.patient.id = :patientId " +
            "AND ev.patientStatus = 'ADMITTED' " +
            "AND ev.dischargeTimestamp IS NULL")
    Optional<EmergencyVisit> findActiveVisitByPatientId(@Param("patientId") Long patientId);

    // get list of visit with status DISCHARGED
    List<EmergencyVisit> findByPatientStatus(EPatientStatus patientStatus);

}
