package hospital.serviceemergency.controller;

import hospital.serviceemergency.model.dto.emergencyvisitstaff.EmergencyVisitStaffDto;
import hospital.serviceemergency.model.enums.EPatientStatus;
import hospital.serviceemergency.service.EmergencyVisitStaffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("${vAPI}/emergency-visit-staff")
public class EmergencyVisitStaffController {
    private final EmergencyVisitStaffService emergencyVisitStaffService;

    public EmergencyVisitStaffController(EmergencyVisitStaffService emergencyVisitStaffService) {
        this.emergencyVisitStaffService = emergencyVisitStaffService;
    }

    /**
     * Get all pageable emergency visit staff
     * @param pageable
     * @return Page of emergency visit staff
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<EmergencyVisitStaffDto>> getAllEmergencyVisitStaff(Pageable pageable, @RequestParam(required = false) EPatientStatus currentStatus) {
        return ResponseEntity.ok(this.emergencyVisitStaffService.getAllEmergencyVisitStaff(pageable, currentStatus));
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

    /**
     * Get all emergency visits between dates
     * @param startDate
     * @param endDate
     * @return List of emergency visits between dates
     */
    @GetMapping(value = "dates", produces = "application/json")
    public ResponseEntity<List<EmergencyVisitStaffDto>> getEmergencyVisitStaffBetweenDates(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endDate) {
        return ResponseEntity.ok(this.emergencyVisitStaffService.getEmergencyVisitStaffBetweenDates(startDate, endDate));
    }

    /**
     * Add emergency visit staff
     * @param emergencyVisitStaffDto
     * @return EmergencyVisitStaffDto
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<EmergencyVisitStaffDto> addEmergencyVisitStaff(@RequestBody EmergencyVisitStaffDto emergencyVisitStaffDto) {
        return ResponseEntity.ok(this.emergencyVisitStaffService.addEmergencyVisitStaff(emergencyVisitStaffDto));
    }

    /**
     * Update emergency visit staff
     * @param emergencyVisitStaffDto
     * @param visitId
     * @param staffId
     * @return EmergencyVisitStaffDto
     */
    @PutMapping(value = "visit/{visitId}/staff/{staffId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<EmergencyVisitStaffDto> updateEmergencyVisitStaff(
            @RequestBody EmergencyVisitStaffDto emergencyVisitStaffDto,
            @PathVariable Long visitId,
            @PathVariable Long staffId) {
        return ResponseEntity.ok(this.emergencyVisitStaffService.updateEmergencyVisitStaff(emergencyVisitStaffDto, visitId, staffId));
    }

    /**
     * Delete emergency visit staff
     * @param visitId
     * @param staffId
     */
    @DeleteMapping(value = "visit/{visitId}/staff/{staffId}")
    public void deleteEmergencyVisitStaff(@PathVariable Long visitId, @PathVariable Long staffId) {
        this.emergencyVisitStaffService.deleteEmergencyVisitStaff(visitId, staffId);
    }
}
