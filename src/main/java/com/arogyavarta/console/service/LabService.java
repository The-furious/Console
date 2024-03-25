package com.arogyavarta.console.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.DTO.LabDTO;
import com.arogyavarta.console.entity.Credentials;
import com.arogyavarta.console.entity.Lab;
import com.arogyavarta.console.entity.UserType;
import com.arogyavarta.console.repo.CredentialsRepository;
import com.arogyavarta.console.repo.LabRepository;

import jakarta.transaction.Transactional;

@Service
public class LabService {

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Transactional
    public void createLab(LabDTO labDTO) {
        Lab lab = new Lab();
        lab.setName(labDTO.getName());
        lab.setEmail(labDTO.getEmail());
        lab.setProfilePhotoUrl(labDTO.getProfilePhotoUrl());
        lab.setContactNumber(labDTO.getContactNumber());
        lab.setAddress(labDTO.getAddress());
        lab.setLocation(labDTO.getLocation());
        lab.setAccreditationNumber(labDTO.getAccreditationNumber());

        labRepository.save(lab);

        Credentials credentials = Credentials.builder()
                .user(lab)
                .username(labDTO.getUsername())
                .password(labDTO.getPassword())
                .userType(UserType.LAB)
                .build();
        credentialsRepository.save(credentials);
    }
}
