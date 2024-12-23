package hospital.serviceemergency.controller;


import hospital.serviceemergency.model.dto.patientvital.DetailPatientVitalDto;
import hospital.serviceemergency.model.dto.patientvital.PatientVitalDto;
import hospital.serviceemergency.service.PatientVitalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/patient-vitals")
public class PatientVitalController {

    private final PatientVitalService patientVitalService;

    public PatientVitalController(PatientVitalService patientVitalService) {
        this.patientVitalService = patientVitalService;
    }

    /**
     * Get all patient vitals paged
     * @param pageable
     * @return Page<PatientVitalDto>
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<PatientVitalDto>> getAllPatientVitals(Pageable pageable) {
        return ResponseEntity.ok(this.patientVitalService.getAllPageablePatientVitals(pageable));
    }

    /**
     * Get all patient vitals made by a staff with a given id
     * @param staffId
     * @param startDate
     * @param finishDate
     * @return List<PatientVitalDto>
     */
    @GetMapping(produces = "application/json", value = "staff/{staffId}")
    public ResponseEntity<List<DetailPatientVitalDto>> getAllStaffPatientVitals(
            @PathVariable Long staffId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") LocalDateTime startDate,
            @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")  LocalDateTime finishDate) {
        return ResponseEntity.ok(this.patientVitalService.getAllStaffPatientVitals(staffId, startDate, finishDate));
    }

    /**
     * Get patient's patient vital
     * @param patientId
     * @param startDate
     * @param finishDate
     * @return List<PatientVitalDto>
     */
    @GetMapping(produces = "application/json", value = "patient/{patientId}")
    public ResponseEntity<List<DetailPatientVitalDto>> getPatientVitals(
            @PathVariable Long patientId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") LocalDateTime startDate,
            @RequestParam(value = "finishDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")  LocalDateTime finishDate) {
        return ResponseEntity.ok(this.patientVitalService.getPatientVitalsOfPatient(patientId, startDate, finishDate));
    }

    /**
     * Add patient vital
     * @param patientVitalDto
     * @return PatientVitalDto
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<DetailPatientVitalDto> addPatientVital(@RequestBody DetailPatientVitalDto patientVitalDto) {
        return ResponseEntity.ok(this.patientVitalService.savePatientVital(patientVitalDto));
    }

    /**
     * Update patient vital
     * @param id
     * @param patientVitalDto
     * @return PatientVitalDto
     */
    @PutMapping(consumes = "application/json", produces = "application/json", value = "{id}")
    public ResponseEntity<DetailPatientVitalDto> updatePatientVital(@RequestBody DetailPatientVitalDto patientVitalDto, @PathVariable Long id) {
        return ResponseEntity.ok(this.patientVitalService.updatePatientVital(patientVitalDto, id));
    }

    /**
     * Delete patient vital
     * @param id
     * @return Boolean
     */
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Boolean> deletePatientVital(@PathVariable Long id) {
        this.patientVitalService.deletePatientVital(id);
        return ResponseEntity.ok(true);
    }


}
