package hospital.serviceemergency.repository;

import hospital.serviceemergency.model.HospitalBed;
import hospital.serviceemergency.model.enums.ECurrentBedStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IHospitalBedRepository extends JpaRepository<HospitalBed, Long> {

    Optional<HospitalBed> findByEmergencyVisit_Patient_Id(Long patientId);

    Optional<HospitalBed> findByBedNumber(String bedNumber);

    List<HospitalBed> findByCurrentStatusAndWardSection(ECurrentBedStatus currentStatus, String wardSection);
}
