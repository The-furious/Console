package com.arogyavarta.console.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.dto.NonConsentDTO;
import com.arogyavarta.console.dto.GiveConsentDTO;
import com.arogyavarta.console.dto.PatientDTO;
import com.arogyavarta.console.entity.Consent;
import com.arogyavarta.console.entity.Consultation;
import com.arogyavarta.console.entity.Credentials;
import com.arogyavarta.console.entity.Patient;
import com.arogyavarta.console.entity.UserType;
import com.arogyavarta.console.repo.ConsentRepository;
import com.arogyavarta.console.repo.ConsultationRepository;
import com.arogyavarta.console.repo.CredentialsRepository;
import com.arogyavarta.console.repo.PatientRepository;
import com.arogyavarta.console.utils.ObjectEncryptionUtility;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private ConsentRepository consentRepository;

    @Transactional
    public void createPatient(PatientDTO patientDTO) throws Exception{
        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setEmail(patientDTO.getEmail());
        patient.setProfilePhotoUrl(patientDTO.getProfilePhotoUrl());
        patient.setContactNumber(patientDTO.getContactNumber());
        patient.setAddress(patientDTO.getAddress());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());
        patient.setWeight(patientDTO.getWeight());
        patient.setHeight(patientDTO.getHeight());
        patient.setBloodGroup(patientDTO.getBloodGroup());
        patient.setEmergencyContact(patientDTO.getEmergencyContact());
        patient.setState(patientDTO.getState());
        patient.setCountry(patientDTO.getCountry());
        patient.setUserType(UserType.PATIENT);
        patient.setPincode(patientDTO.getPincode());
        patient.setUserType(UserType.PATIENT);

        ObjectEncryptionUtility.encryptStringFields(patient);

        patientRepository.save(patient);


        Credentials credentials = Credentials.builder()
                .user(patient)
                .username(patientDTO.getUsername())
                .password(passwordEncoder.encode(patientDTO.getPassword()))
                .userType(UserType.PATIENT)
                .build();
        credentialsRepository.save(credentials);
    }

    public Patient getPatientById(Long patientId) throws Exception{
        Patient patient =  patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));
        ObjectEncryptionUtility.decryptStringFields(patient);
        return patient;
    }

    public List<NonConsentDTO> getAllUngivenConsents(Long userId) {
        List<Consultation> consultations=consultationRepository.findAllByPatientId(userId);
        Set<Long> userIds = new HashSet<>();
        for (Consultation consultation : consultations) {
            userIds.add(consultation.getConsultationId());
        }
        List<Consent> consents=consentRepository.findByGivenConsentAndUserIdIn(0, userIds);
        return consents.stream().map(this::mapConsentToNonConsentDTO).collect(Collectors.toList());
    }

    private NonConsentDTO mapConsentToNonConsentDTO(Consent consent) {
        NonConsentDTO nonConsentDTO=new NonConsentDTO();
        nonConsentDTO.setConsultationId(consent.getConsultation().getConsultationId());
        nonConsentDTO.setUserId(consent.getUser().getUserId());
        nonConsentDTO.setName(consent.getUser().getName());
        return nonConsentDTO;
    }

    public void  updateProfilePhoto(Long id, String fileName)
    {
        Patient patient=patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Radiologist not found"));
        patient.setProfilePhotoUrl(fileName);
        patientRepository.save(patient);
    }

    public void updatePatient(PatientDTO patientDTO){



    }

    public void giveConsent(GiveConsentDTO giveConsentDTO) {
        Consent consent = consentRepository.findByConsultationIdAndUserId(giveConsentDTO.getConsultationId(), giveConsentDTO.getUserId()).orElseThrow();
        if( giveConsentDTO.getConsentGiven()){
            consent.setGivenConsent(true);
            consentRepository.save(consent);
        }
        else{
            consentRepository.delete(consent);
        }
    }
}
