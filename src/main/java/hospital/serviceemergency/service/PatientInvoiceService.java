package hospital.serviceemergency.service;


import hospital.serviceemergency.model.MedicalProcedure;
import hospital.serviceemergency.model.PatientInvoce;
import hospital.serviceemergency.model.PatientVital;
import hospital.serviceemergency.model.dto.patientinvoice.PatientInvoiceDto;
import hospital.serviceemergency.model.dto.patientvital.DetailPatientVitalDto;
import hospital.serviceemergency.model.dto.patientvital.PatientVitalDto;
import hospital.serviceemergency.repository.IMedicalProcedureRepository;
import hospital.serviceemergency.repository.IPatientInvoiceRepository;
import hospital.serviceemergency.repository.IPatientVitalRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/* Service CRUD Patient Invoice */
@Service
public class PatientInvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(PatientInvoiceService.class);

    private final IPatientInvoiceRepository patientInvoiceRepository;
    private final IMedicalProcedureRepository medicalProcedureRepository;
    private final ModelMapper modelMapper;

    public PatientInvoiceService(IPatientInvoiceRepository patientInvoiceRepository, IMedicalProcedureRepository medicalProcedureRepository, ModelMapper modelMapper) {
        this.patientInvoiceRepository = patientInvoiceRepository;
        this.modelMapper = modelMapper;
        this.medicalProcedureRepository = medicalProcedureRepository;
    }

    /**
     * Get all pageable patient invoices
     */
    public Page<PatientInvoiceDto> getAllPageablePatientInvoice(Pageable pageable) {
        return patientInvoiceRepository.findAll(pageable).map(this::convertToDto);
    }

    /**
     * Get patient invoice by id
     * @param id
     */
    public PatientInvoiceDto getPatientInvoiceById(Long id) {
        PatientInvoce patientInvoce = patientInvoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient invoice with id " + id + " not found"));
        return convertToDto(patientInvoce);
    }

    // Get invoice by patient id
    public List<PatientInvoiceDto> getInvoiceByPatientId(Long patientId) {
        List<PatientInvoce> patientInvoce = patientInvoiceRepository.findByEmergencyVisit_Patient_Id(patientId);
        return patientInvoce.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Get invoice by visit id
    public PatientInvoiceDto getInvoiceByVisitId(Long visitId) {
        PatientInvoce patientInvoce = patientInvoiceRepository.findByEmergencyVisit_Id(visitId);
        return patientInvoce == null ? null : convertToDto(patientInvoce);
    }


    /**
     * Save patient invoice
     */
    public PatientInvoiceDto savePatientInvoice(PatientInvoiceDto patientInvoiceDto) {
        PatientInvoce patientInvoce = convertToDao(patientInvoiceDto);
        PatientInvoce savePatientInvoice  = patientInvoiceRepository.save(patientInvoce);
        return convertToDto(savePatientInvoice);
    }

    // Get all medical procedure by visit id and sum up the procedure cost to get total amount
    public Float getTotalAmountByVisitId(Long visitId) {
        List<MedicalProcedure> medicalProcedures = medicalProcedureRepository.findByEmergencyVisit_Id(visitId);
        return medicalProcedures.stream().map(MedicalProcedure::getProcedureCost).reduce(0f, Float::sum);
    }

    /**
     * Update patient vital
     */
    public PatientInvoiceDto updatePatientInvoice(PatientInvoiceDto patientInvoiceDto, Long id) {
        boolean existsById = patientInvoiceRepository.existsById(id);
        if (!existsById) {
            logger.error("Patient invoice with id {} not found", id);
            throw new IllegalArgumentException("Patient invoice with id " + id + " not found");
        }
        PatientInvoce patientInvoce = convertToDao(patientInvoiceDto);
        patientInvoce.setId(id);
        PatientInvoce savePatientInvoice = patientInvoiceRepository.save(patientInvoce);
        return convertToDto(savePatientInvoice);
    }

    /**
     * Delete patient vital
     */
    public void deletePatientInvoice(Long id) {
        boolean existsById = patientInvoiceRepository.existsById(id);
        if (!existsById) {
            logger.error("Patient invoice with id {} not found", id);
            throw new IllegalArgumentException("Patient invoice with id " + id + " not found");
        }
        patientInvoiceRepository.deleteById(id);
    }

    // Converters
    private PatientInvoiceDto convertToDto(PatientInvoce patientInvoce) {
        return modelMapper.map(patientInvoce, PatientInvoiceDto.class);
    }
    private PatientInvoce convertToDao(PatientInvoiceDto patientVitalDto) {
        return modelMapper.map(patientVitalDto, PatientInvoce.class);
    }


}
