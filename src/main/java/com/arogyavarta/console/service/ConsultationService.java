package com.arogyavarta.console.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.arogyavarta.console.DTO.ConsentNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.DTO.ConsultationDTO;
import com.arogyavarta.console.config.Constants;
import com.arogyavarta.console.entity.Consent;
import com.arogyavarta.console.entity.Consultation;
import com.arogyavarta.console.entity.Doctor;
import com.arogyavarta.console.entity.Patient;
import com.arogyavarta.console.entity.UserType;
import com.arogyavarta.console.repo.ConsentRepository;
import com.arogyavarta.console.repo.ConsultationRepository;
import com.arogyavarta.console.repo.DoctorRepository;
import com.arogyavarta.console.repo.PatientRepository;

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


    public List<ConsentNameDTO> getGivenConsent(Long consultationId) {
        List<Consent> consent = consentRepository.findAllByUserIdAndConsultationId(consultationId);
        return consent.stream().map(this::mapComsentToConsentNameDTO).collect(Collectors.toList());
    }
    private ConsentNameDTO mapComsentToConsentNameDTO(Consent consent) {
        ConsentNameDTO consentNameDTO = new ConsentNameDTO();
        consentNameDTO.setGivenConsent(consent.getGivenConsent());
        consentNameDTO.setUserId(consent.getUser().getUserId());
        return consentNameDTO;
    }
}
