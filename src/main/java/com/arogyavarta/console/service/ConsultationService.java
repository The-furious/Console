package com.arogyavarta.console.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.arogyavarta.console.dto.ConsentNameDTO;
import com.arogyavarta.console.entity.*;
import com.arogyavarta.console.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.dto.ConsultationDTO;
import com.arogyavarta.console.config.Constants;

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
}
