package com.arogyavarta.console.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.DTO.PatientDTO;
import com.arogyavarta.console.entity.Credentials;
import com.arogyavarta.console.entity.Patient;
import com.arogyavarta.console.entity.UserType;
import com.arogyavarta.console.repo.CredentialsRepository;
import com.arogyavarta.console.repo.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Transactional
    public void createPatient(PatientDTO patientDTO) {
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
        patient.setPincode(patientDTO.getPincode());

        patientRepository.save(patient);


        Credentials credentials = Credentials.builder()
                .user(patient)
                .username(patientDTO.getUsername())
                .password(patientDTO.getPassword())
                .userType(UserType.PATIENT)
                .build();
        credentialsRepository.save(credentials);
    }
}
