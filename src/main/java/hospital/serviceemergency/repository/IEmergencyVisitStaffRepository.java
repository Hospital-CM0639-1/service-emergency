package hospital.serviceemergency.repository;

import hospital.serviceemergency.model.EmergencyVisitStaff;
import hospital.serviceemergency.model.EmergencyVisitStaffId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IEmergencyVisitStaffRepository extends JpaRepository<EmergencyVisitStaff, EmergencyVisitStaffId> {
    @Query("SELECT evs FROM EmergencyVisitStaff evs " +
            "JOIN FETCH evs.emergencyVisit ev " +
            "JOIN FETCH ev.patient p " +
            "WHERE evs.staff.id = :staffId")
    List<EmergencyVisitStaff> findPatientsAssignedToStaff(@Param("staffId") Long staffId);

    @Query("SELECT evs FROM EmergencyVisitStaff evs " +
            "JOIN FETCH evs.staff s " +
            "JOIN FETCH evs.emergencyVisit ev " +
            "WHERE ev.patient.id = :patientId")
    List<EmergencyVisitStaff> findStaffAssignedToPatient(@Param("patientId") Long patientId);

    @Query("SELECT evs FROM EmergencyVisitStaff evs " +
            "JOIN FETCH evs.staff s " +
            "JOIN FETCH evs.emergencyVisit ev " +
            "JOIN FETCH ev.patient p " +
            "WHERE ev.id = :visitId")
    List<EmergencyVisitStaff> findPatientAndStaffInvolvedInVisit(@Param("visitId") Long visitId);

    @Query("SELECT CASE WHEN COUNT(evs) > 0 THEN true ELSE false END " +
            "FROM EmergencyVisitStaff evs " +
            "WHERE evs.visitId = :visitId AND evs.staffId = :staffId")
    boolean existsById(@Param("visitId") Long visitId, @Param("staffId") Long staffId);

    @Query("SELECT evs FROM EmergencyVisitStaff evs " +
            "WHERE evs.assignedAt BETWEEN :startDate AND :endDate")
    List<EmergencyVisitStaff> findEmergencyVisitStaffBetweenDates(@Param("startDate") LocalDateTime startDate,
                                                                  @Param("endDate") LocalDateTime endDate);


    // Delete by visit id and staff id
    void deleteByVisitIdAndStaffId(Long visitId, Long staffId);
}
