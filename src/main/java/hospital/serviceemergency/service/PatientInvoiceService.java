package hospital.serviceemergency.service;


import hospital.serviceemergency.model.PatientInvoce;
import hospital.serviceemergency.model.PatientVital;
import hospital.serviceemergency.model.dto.patientinvoice.PatientInvoiceDto;
import hospital.serviceemergency.model.dto.patientvital.DetailPatientVitalDto;
import hospital.serviceemergency.model.dto.patientvital.PatientVitalDto;
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
    private final ModelMapper modelMapper;

    public PatientInvoiceService(IPatientInvoiceRepository patientInvoiceRepository, ModelMapper modelMapper) {
        this.patientInvoiceRepository = patientInvoiceRepository;
        this.modelMapper = modelMapper;
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


    /**
     * Save patient invoice
     */
    public PatientInvoiceDto savePatientInvoice(PatientInvoiceDto patientInvoiceDto) {
        PatientInvoce patientInvoce = convertToDao(patientInvoiceDto);
        PatientInvoce savePatientInvoice  = patientInvoiceRepository.save(patientInvoce);
        return convertToDto(savePatientInvoice);
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
