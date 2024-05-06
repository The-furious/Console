package com.arogyavarta.console.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arogyavarta.console.config.Constants;
import com.arogyavarta.console.dto.ConsentNameDTO;
import com.arogyavarta.console.dto.ConsultationDTO;
import com.arogyavarta.console.dto.EndConsultationDTO;
import com.arogyavarta.console.dto.ImageDTO;
import com.arogyavarta.console.entity.Consent;
import com.arogyavarta.console.entity.Consultation;
import com.arogyavarta.console.entity.Credentials;
import com.arogyavarta.console.entity.Doctor;
import com.arogyavarta.console.entity.Images;
import com.arogyavarta.console.entity.Patient;
import com.arogyavarta.console.entity.Prescription;
import com.arogyavarta.console.entity.Tests;
import com.arogyavarta.console.entity.UserType;
import com.arogyavarta.console.repo.ConsentRepository;
import com.arogyavarta.console.repo.ConsultationRepository;
import com.arogyavarta.console.repo.CredentialsRepository;
import com.arogyavarta.console.repo.DoctorRepository;
import com.arogyavarta.console.repo.ImagesRepository;
import com.arogyavarta.console.repo.PatientRepository;
import com.arogyavarta.console.repo.PrescriptionRepository;
import com.arogyavarta.console.repo.TestsRepository;
import com.arogyavarta.console.utils.EmailUtility;
import com.arogyavarta.console.utils.PDFGenerator;
import com.arogyavarta.console.utils.StorageUtil;

import jakarta.transaction.Transactional;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ConsentRepository consentRepository;

    @Autowired
    private CredentialsRepository credentialRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private TestsRepository testsRepository;

    @Autowired
    private StorageUtil storageUtil;

    @Transactional
    public Consultation createConsultation(ConsultationDTO consultationDTO) {
        if (consultationDTO == null || consultationDTO.getDoctorId() == null || consultationDTO.getPatientId() == null) {
            throw new IllegalArgumentException("ConsultationDTO is invalid");
        }
        Consultation existing = consultationRepository.findTopByPatientUserIdAndDoctorUserIdOrderByEndDateDesc(consultationDTO.getPatientId(), consultationDTO.getDoctorId());

        if( existing!=null && existing.getEndDate().compareTo(LocalDateTime.now()) > 0)
        {
            return existing;
        }

        Doctor doctor = doctorRepository.findById(consultationDTO.getDoctorId())
                                        .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + consultationDTO.getDoctorId()));
        Patient patient = patientRepository.findById(consultationDTO.getPatientId())
                                            .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + consultationDTO.getPatientId()));

        Consultation consultation = Consultation.builder()
                                                .patient(patient)
                                                .doctor(doctor)
                                                .startDate(LocalDateTime.now())
                                                .endDate(Constants.MAX_DATE)
                                                .build();

        Consultation newConsultation = consultationRepository.save(consultation);

        Consent consent = Consent.builder()
                                 .userType(UserType.DOCTOR)
                                 .consentDate(LocalDateTime.now())
                                 .givenConsent(true)
                                 .user(doctor)
                                 .consultation(newConsultation)
                                 .build();
        consentRepository.save(consent);

        Consent consentPatient = Consent.builder()
                .userType(UserType.PATIENT)
                .consentDate(LocalDateTime.now())
                .givenConsent(true)
                .user(patient)
                .consultation(newConsultation)
                .build();
        consentRepository.save(consentPatient);
        return newConsultation;  
    }
    public List<Consultation> getConsultationHistory(Long userId){
        List<String> consentIds=consentRepository.findConsultationIdsByConsentAndUserId(userId);
        LocalDateTime currentDate = LocalDateTime.now();
        return consultationRepository.findByConsultationIdInAndEndDateBefore(consentIds, currentDate);
    }
    public List<Consultation> getConsultationPresent(Long userId){
        List<String> consentIds=consentRepository.findConsultationIdsByConsentAndUserId(userId);
        LocalDateTime currentDate = LocalDateTime.now();
        return consultationRepository.findByConsultationIdInAndEndDateAfter(consentIds, currentDate);

    }

    public List<ConsentNameDTO> getGivenConsent(Long consultationId, Long userId) {
        Optional<Consent> consents=consentRepository.findByConsultationIdAndUserId(consultationId,userId);
        if(consents.isEmpty()) return new ArrayList<>();
        Credentials credentials=credentialRepository.findUserById(userId);
        UserType userType=credentials.getUserType();
        if(userType.equals(UserType.DOCTOR)){
            List<Consent> consent = consentRepository.findAllByUserIdAndConsultationIdNotDoctor(consultationId,userId);
            return consent.stream().filter(cons -> cons.getUserType() != UserType.LAB).map(this::mapConsentToConsentNameDTO).collect(Collectors.toList());
        }
        List<Consultation> consultation = consultationRepository.findAllDoctorByConsultationId(consultationId);
        return consultation.stream().map(this::mapDoctorToConsentNameDTO).collect(Collectors.toList());
    }
    private ConsentNameDTO mapConsentToConsentNameDTO(Consent consent) {
        ConsentNameDTO consentNameDTO = new ConsentNameDTO();
        consentNameDTO.setGivenConsent(consent.getGivenConsent());
        consentNameDTO.setUserType(consent.getUserType().name());
        consentNameDTO.setName(consent.getUser().getName());
        consentNameDTO.setUserId(consent.getUser().getUserId());
        return consentNameDTO;
    }
    private ConsentNameDTO mapDoctorToConsentNameDTO(Consultation consultation) {
        ConsentNameDTO consentNameDTO = new ConsentNameDTO();
        consentNameDTO.setGivenConsent(Boolean.TRUE);
        consentNameDTO.setUserType(UserType.DOCTOR.name());
        consentNameDTO.setName(consultation.getDoctor().getName());
        consentNameDTO.setUserId(consultation.getDoctor().getUserId());
        return consentNameDTO;
    }

    @Transactional
    public Consultation endConsultation(EndConsultationDTO endConsultationDTO) {
        Consultation currConsultation = consultationRepository.findById(endConsultationDTO.getConsultationId()).orElseThrow();
        if (currConsultation.getEndDate().isBefore(LocalDateTime.now())) {
            return currConsultation;
        }
        Prescription prescription = Prescription.builder().consultation(currConsultation)
                                                          .impression(endConsultationDTO.getImpression())
                                                          .prescriptions(endConsultationDTO.getPrescription())
                                                          .build();
        prescriptionRepository.save(prescription);
        currConsultation.setEndDate(LocalDateTime.now());
        consultationRepository.save(currConsultation);

        // generate report
        MultipartFile file = generateReport(currConsultation, prescription);
        
        // sent mail
        String subject = Constants.END_CONSULTATION_SUBJECT;
        String body = Constants.END_CONSULTATION_BODY;
        body = body.replace("$patient_name", currConsultation.getPatient().getName())
             .replace("$doctor_name", currConsultation.getDoctor().getName());
        try {
            EmailUtility.sendReport(currConsultation.getPatient().getEmail(), subject, body, file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return currConsultation;
    }

    private ImageDTO convertImagesToImageDTO(Images image)
    {
        return ImageDTO.builder()
                        .id(image.getImageId())
                        .imageUrl(storageUtil.generatePresignedUrl(image.getImageUrl()))
                        .build();
    }

    private MultipartFile generateReport(Consultation currConsultation, Prescription prescription)
    {
        Tests test = testsRepository.findTopByConsultationConsultationId(currConsultation.getConsultationId());
        List<Images> images = imagesRepository.findByTestsTestId(test.getTestId());
        List<ImageDTO> imageDTO = images.stream().map(t-> convertImagesToImageDTO(t)).collect(Collectors.toList());
        return PDFGenerator.generateReport(currConsultation, prescription, test, imageDTO, "report.pdf");
    }
    public MultipartFile getReport(Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId).orElseThrow();
        Prescription prescription = prescriptionRepository.findByConsultation_ConsultationId(consultationId);
        return generateReport(consultation, prescription);
    }
}
