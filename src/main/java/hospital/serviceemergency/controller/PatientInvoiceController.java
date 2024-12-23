package hospital.serviceemergency.controller;


import hospital.serviceemergency.model.dto.patientinvoice.PatientInvoiceDto;
import hospital.serviceemergency.service.PatientInvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${vAPI}/patient-invoices")
public class PatientInvoiceController {

    private final PatientInvoiceService patientInvoiceService;

    public PatientInvoiceController(PatientInvoiceService patientInvoiceService) {
        this.patientInvoiceService = patientInvoiceService;
    }

    /**
     * Get all patient invoices paged
     * @param pageable
     * @return Page<PatientInvoiceDto>
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<PatientInvoiceDto>> getAllPatientInvoices(Pageable pageable) {
        return ResponseEntity.ok(this.patientInvoiceService.getAllPageablePatientInvoice(pageable));
    }

    /**
     * Get patient invoice by id
     * @param id
     * @return PatientInvoiceDto
     */
    @GetMapping(produces = "application/json", value = "{id}")
    public ResponseEntity<PatientInvoiceDto> getPatientInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(this.patientInvoiceService.getPatientInvoiceById(id));
    }

    /**
     * Get patient invoice by patient id
     * @param patientId
     * @return List<PatientInvoiceDto>
     */
    @GetMapping(produces = "application/json", value = "/patient/{patientId}")
    public ResponseEntity<List<PatientInvoiceDto>> getInvoiceByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(this.patientInvoiceService.getInvoiceByPatientId(patientId));
    }

    /**
     * Get patient invoice by visit id
     * @param visitId
     * @return List<PatientInvoiceDto>
     */
    @GetMapping(produces = "application/json", value = "/visit/{visitId}")
    public ResponseEntity<List<PatientInvoiceDto>> getInvoiceByVisitId(@PathVariable Long visitId) {
        return ResponseEntity.ok(this.patientInvoiceService.getInvoiceByVisitId(visitId));
    }

    /**
     * Add patient invoice
     * @param patientInvoiceDto
     * @return PatientVitalDto
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PatientInvoiceDto> addPatientInvoice(@RequestBody PatientInvoiceDto patientInvoiceDto) {
        return ResponseEntity.ok(this.patientInvoiceService.savePatientInvoice(patientInvoiceDto));
    }

    /**
     * Update patient invoice
     * @param id
     * @param patientInvoiceDto
     * @return PatientInvoiceDto
     */
    @PutMapping(consumes = "application/json", produces = "application/json", value = "{id}")
    public ResponseEntity<PatientInvoiceDto> updatePatientInvoice(@PathVariable Long id, @RequestBody PatientInvoiceDto patientInvoiceDto) {
        patientInvoiceDto.setId(id);
        return ResponseEntity.ok(this.patientInvoiceService.savePatientInvoice(patientInvoiceDto));
    }

    /**
     * Delete patient invoice
     * @param id
     * @return Boolean
     */
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Boolean> deletePatientInvoice(@PathVariable Long id) {
        this.patientInvoiceService.deletePatientInvoice(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }


}
