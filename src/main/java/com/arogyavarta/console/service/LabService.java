package com.arogyavarta.console.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arogyavarta.console.DTO.LabDTO;
import com.arogyavarta.console.DTO.LabUploadDTO;
import com.arogyavarta.console.entity.Consent;
import com.arogyavarta.console.entity.Consultation;
import com.arogyavarta.console.entity.Credentials;
import com.arogyavarta.console.entity.Images;
import com.arogyavarta.console.entity.Lab;
import com.arogyavarta.console.entity.Tests;
import com.arogyavarta.console.entity.UserType;
import com.arogyavarta.console.repo.ConsentRepository;
import com.arogyavarta.console.repo.ConsultationRepository;
import com.arogyavarta.console.repo.CredentialsRepository;
import com.arogyavarta.console.repo.ImagesRepository;
import com.arogyavarta.console.repo.LabRepository;
import com.arogyavarta.console.repo.TestsRepository;
import com.arogyavarta.console.utils.StorageUtil;

import jakarta.transaction.Transactional;

@Service
public class LabService {

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private StorageUtil storageUtil;

    @Autowired
    private TestsRepository testsRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private ConsentRepository consentRepository;

    @Transactional
    public void createLab(LabDTO labDTO) {
        Lab lab = new Lab();
        lab.setName(labDTO.getName());
        lab.setEmail(labDTO.getEmail());
        lab.setProfilePhotoUrl(labDTO.getProfilePhotoUrl());
        lab.setContactNumber(labDTO.getContactNumber());
        lab.setAddress(labDTO.getAddress());
        lab.setAccreditationNumber(labDTO.getAccreditationNumber());

        labRepository.save(lab);

        Credentials credentials = Credentials.builder()
                .user(lab)
                .username(labDTO.getUsername())
                .password(passwordEncoder.encode(labDTO.getPassword()))
                .userType(UserType.LAB)
                .build();
        credentialsRepository.save(credentials);
    }

    public List<Lab> getAllLabs() {
        return labRepository.findAll();
    }

    @Transactional
    public String upload(LabUploadDTO uploadDTO) {
        Optional<Consultation> consultation = consultationRepository.findById(uploadDTO.getConsultationId());
        if (!consultation.isPresent() && consultation.get().getEndDate().isBefore(LocalDateTime.now())) {
            return "Error: consultation Id does not exist";
        }
        Lab lab = labRepository.findById(uploadDTO.getLabId()).orElseThrow();

        // giving consent to lab (can think of better approch)
        Consent consent = Consent.builder()
                                 .userType(UserType.LAB)
                                 .consentDate(LocalDateTime.now())
                                 .givenConsent(true)
                                 .user(lab)
                                 .consultation(consultation.get())
                                 .build();
        consentRepository.save(consent);

        Tests test = Tests.builder()
                        .remarks(uploadDTO.getRemarks())
                        .testName(uploadDTO.getTestName())
                        .consultation(consultation.get())
                        .testDate(LocalDateTime.now())
                        .lab(lab)
                        .build();
        test = testsRepository.save(test);

        for (MultipartFile file : uploadDTO.getFiles()) {
            String fileName = storageUtil.uploadFile(file);
            Images image = Images.builder().tests(test).imageUrl(fileName).build();
            imagesRepository.save(image);
        }
        
        return "Uploaded";
        
    }
}
