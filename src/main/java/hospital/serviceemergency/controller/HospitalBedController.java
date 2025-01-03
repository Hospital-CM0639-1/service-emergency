package hospital.serviceemergency.controller;

import hospital.serviceemergency.model.dto.hospitalbed.DetailHospitalBedDto;
import hospital.serviceemergency.model.dto.hospitalbed.HospitalBedDto;
import hospital.serviceemergency.model.dto.hospitalbed.PatientBedAssignmentDto;
import hospital.serviceemergency.model.enums.ECurrentBedStatus;
import hospital.serviceemergency.model.enums.EWardSection;
import hospital.serviceemergency.service.HospitalBedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${vAPI}/hospital-beds")
public class HospitalBedController {
    private final HospitalBedService hospitalBedService;

    public HospitalBedController(HospitalBedService hospitalBedService) {
        this.hospitalBedService = hospitalBedService;
    }

    /**
     * Get all hospital beds
     * @param pageable
     * @return Page<HospitalBedDto>
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<HospitalBedDto>> getAllHospitalBeds(Pageable pageable) {
        return ResponseEntity.ok(this.hospitalBedService.getAllPageableHospitalBeds(pageable));
    }

    /**
     * Get hospital bed by id
     * @param hospitalBedId
     * @return HospitalBedDto
     */
    @GetMapping(produces = "application/json", value = "/{hospitalBedId}")
    public ResponseEntity<DetailHospitalBedDto> getHospitalBedById(@PathVariable Long hospitalBedId) {
        return ResponseEntity.ok(this.hospitalBedService.getHospitalBedById(hospitalBedId));
    }

    /**
     * Get hospital bed by patient id
     * @param patientId
     * @return DetailHospitalBedDto
     */
    @GetMapping(produces = "application/json", value = "/patient/{patientId}")
    public ResponseEntity<DetailHospitalBedDto> getHospitalBedByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(this.hospitalBedService.getHospitalBedByPatientId(patientId));
    }

    /**
     * Get hospital bed by bed number
     * @param bedNumber
     * @return HospitalBedDto
     */
    @GetMapping(produces = "application/json", value = "/bed-number/{bedNumber}")
    public ResponseEntity<DetailHospitalBedDto> getHospitalBedByBedNumber(@PathVariable String bedNumber) {
        return ResponseEntity.ok(this.hospitalBedService.getHospitalBedByBedNumber(bedNumber));
    }

    /**
     * Get hospital beds by current status and ward section
     * @param currentStatus
     * @param wardSection
     * @return List<HospitalBedDto>
     */
    @GetMapping(produces = "application/json", value = "/status/{currentStatus}/ward-section/{wardSection}")
    public ResponseEntity<List<HospitalBedDto>> getHospitalBedsByCurrentStatusAndWardSection(@PathVariable ECurrentBedStatus currentStatus,
                                                                                             @PathVariable EWardSection wardSection) {
        return ResponseEntity.ok(this.hospitalBedService.getHospitalBedsByCurrentStatusAndWardSection(currentStatus, wardSection));
    }

    /**
     * Save hospital bed
     * @param hospitalBedDto
     * @return DetailHospitalBedDto
     */
    @PostMapping(produces = "application/json")
    public ResponseEntity<DetailHospitalBedDto> saveHospitalBed(@RequestBody DetailHospitalBedDto hospitalBedDto) {
        return ResponseEntity.ok(this.hospitalBedService.saveHospitalBed(hospitalBedDto));
    }

    /**
     * Update hospital bed
     * @param hospitalBedDto
     * @param id
     * @return DetailHospitalBedDto
     */
    @PutMapping(produces = "application/json", value = "/{id}")
    public ResponseEntity<DetailHospitalBedDto> updateHospitalBed(@RequestBody DetailHospitalBedDto hospitalBedDto,
                                                            @PathVariable Long id) {
        return ResponseEntity.ok(this.hospitalBedService.updateHospitalBed(hospitalBedDto, id));
    }

    // Assign patient to hospital bed
    @PutMapping(produces = "application/json", value = "/assign-patient/{patientId}/hospital-bed/{hospitalBedId}/status/{currentStatus}")
    public ResponseEntity<DetailHospitalBedDto> assignPatientToHospitalBed(@PathVariable Long patientId,
                                                                           @PathVariable Long hospitalBedId,
                                                                           @PathVariable ECurrentBedStatus currentStatus) {
        return ResponseEntity.ok(this.hospitalBedService.assignPatientToHospitalBed(patientId, hospitalBedId, currentStatus));
    }

    // Free hospital bed by patient id
    @PutMapping(produces = "application/json", value = "/free-bed/patient/{patientId}")
    public ResponseEntity<DetailHospitalBedDto> freeHospitalBedByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(this.hospitalBedService.freeBedByPatientId(patientId));
    }



    // find patient needing bed
    @GetMapping(produces = "application/json", value = "/patients-needing-beds")
    public ResponseEntity<List<PatientBedAssignmentDto>> findPatientsNeedingBeds() {
        return ResponseEntity.ok(this.hospitalBedService.getPatientsNeedingBeds());
    }

    /**
     * Delete hospital bed
     * @param id
     * @return Boolean
     */
    @DeleteMapping(produces = "application/json", value = "/{id}")
    public ResponseEntity<Boolean> deleteHospitalBed(@PathVariable Long id) {
        this.hospitalBedService.deleteHospitalBed(id);
        return ResponseEntity.ok(true);
    }

}
