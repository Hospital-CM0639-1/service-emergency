package hospital.serviceemergency.controller;

import hospital.serviceemergency.model.dto.emergencyvisitstaff.EmergencyVisitStaffDto;
import hospital.serviceemergency.service.EmergencyVisitStaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/emergency-visit-staff")
public class EmergencyVisitStaffController {
    private final EmergencyVisitStaffService emergencyVisitStaffService;

    public EmergencyVisitStaffController(EmergencyVisitStaffService emergencyVisitStaffService) {
        this.emergencyVisitStaffService = emergencyVisitStaffService;
    }

    /**
     * Get all staff assigned to a patient
     * @param staffId
     * @return List of patients assigned to a staff
     */
    @GetMapping(value = "staff/{staffId}", produces = "application/json")
    public ResponseEntity<List<EmergencyVisitStaffDto>> getPatientsAssignedToStaff(@PathVariable Long staffId) {
        return ResponseEntity.ok(this.emergencyVisitStaffService.getPatientsAssignedToStaff(staffId));
    }

    /**
     * Get all staff assigned to a patient
     * @param patientId
     * @return List of staff assigned to a patient
     */
    @GetMapping(value = "patient/{patientId}", produces = "application/json")
    public ResponseEntity<List<EmergencyVisitStaffDto>> getStaffAssignedToPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(this.emergencyVisitStaffService.getStaffAssignedToPatient(patientId));
    }

    /**
     * Get all staff and patients involved in a visit
     * @param visitId
     * @return List of staff and patients involved in a visit
     */
    @GetMapping(value = "visit/{visitId}", produces = "application/json")
    public ResponseEntity<List<EmergencyVisitStaffDto>> getPatientAndStaffInvolvedInVisit(@PathVariable Long visitId) {
        return ResponseEntity.ok(this.emergencyVisitStaffService.getPatientAndStaffInvolvedInVisit(visitId));
    }
}
