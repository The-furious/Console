package com.arogyavarta.console.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.dto.RadiologistDTO;
import com.arogyavarta.console.entity.Credentials;
import com.arogyavarta.console.entity.Radiologist;
import com.arogyavarta.console.entity.UserType;
import com.arogyavarta.console.repo.CredentialsRepository;
import com.arogyavarta.console.repo.RadiologistRepository;

import jakarta.transaction.Transactional;

@Service
public class RadiologistService {

    @Autowired
    private RadiologistRepository radiologistRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createRadiologist(RadiologistDTO radiologistDTO) {
        Radiologist radiologist = new Radiologist();

        radiologist.setName(radiologistDTO.getName());
        radiologist.setEmail(radiologistDTO.getEmail());
        radiologist.setProfilePhotoUrl(radiologistDTO.getProfilePhotoUrl());
        radiologist.setContactNumber(radiologistDTO.getContactNumber());
        radiologist.setAddress(radiologistDTO.getAddress());
        radiologist.setUserType(UserType.RADIOLOGIST);
        radiologist.setSpecialization(radiologistDTO.getSpecialization());
        radiologist.setLicenseNumber(radiologistDTO.getLicenseNumber());

        radiologistRepository.save(radiologist);

        Credentials credentials = Credentials.builder()
                .user(radiologist)
                .username(radiologistDTO.getUsername())
                .password(passwordEncoder.encode(radiologistDTO.getPassword()))
                .userType(UserType.RADIOLOGIST)
                .build();
        credentialsRepository.save(credentials);
    }

    public List<Radiologist> getAllRadiologists() {
        return radiologistRepository.findAll();
    }

}
