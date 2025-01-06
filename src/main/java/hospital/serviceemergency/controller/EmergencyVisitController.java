package hospital.serviceemergency.controller;

import hospital.serviceemergency.model.EmergencyVisit;
import hospital.serviceemergency.model.PatientInvoce;
import hospital.serviceemergency.model.dto.emergencyvisit.EmergencyVisitDto;
import hospital.serviceemergency.model.enums.EPatientStatus;
import hospital.serviceemergency.repository.IEmergencyVisitRepository;
import hospital.serviceemergency.repository.IPatientInvoiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("${vAPI}/emergency-visit")
public class EmergencyVisitController {
    private final IEmergencyVisitRepository emergencyVisitRepository;
    private final IPatientInvoiceRepository patientInvoiceRepository;
    private final ModelMapper modelMapper;

    public EmergencyVisitController(IEmergencyVisitRepository emergencyVisitRepository, IPatientInvoiceRepository patientInvoiceRepository, ModelMapper modelMapper) {
        this.emergencyVisitRepository = emergencyVisitRepository;
        this.modelMapper = modelMapper;
        this.patientInvoiceRepository = patientInvoiceRepository;
    }

    // get by id
    @GetMapping(produces = "application/json", value = "{id}")
    public ResponseEntity<EmergencyVisitDto> getAllEmergencyVisitStaff(@PathVariable Long id) {
        return ResponseEntity.ok(this.emergencyVisitRepository.findById(id).map(this::convertToDto)
                .orElseThrow(() -> new IllegalArgumentException("Emergency visit with id " + id + " not found")));
    }

    // get visit with status DISCHARGED
    @GetMapping(produces = "application/json", value = "/discharged")
    public ResponseEntity<List<EmergencyVisitDto>> getDischargedEmergencyVisit() {
        List<EmergencyVisitDto> emergencyVisitDtos = this.emergencyVisitRepository.findByPatientStatus(EPatientStatus.DISCHARGED).stream()
                .map(this::convertToDto)
                .toList();
        // for each emergency visit, check if in patient invoice table the status is pending for his fk otherwise remove from list
        List<EmergencyVisitDto> emergencyPendingVisit = new ArrayList<>();
        for (EmergencyVisitDto emergencyVisitDto : emergencyVisitDtos) {
            PatientInvoce patientInvoce = patientInvoiceRepository.findByEmergencyVisit_Id(emergencyVisitDto.getId());
            if (patientInvoce != null && !patientInvoce.getPaymentStatus().equals("pending")) {
                emergencyPendingVisit.add(emergencyVisitDto);
            }
        }
        return ResponseEntity.ok(emergencyPendingVisit);
    }

    // convert to dto
    private EmergencyVisitDto convertToDto(EmergencyVisit emergencyVisit) {
        return modelMapper.map(emergencyVisit, EmergencyVisitDto.class);
    }

}
