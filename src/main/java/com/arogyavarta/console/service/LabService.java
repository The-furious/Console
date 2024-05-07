package com.arogyavarta.console.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arogyavarta.console.dto.LabDTO;
import com.arogyavarta.console.dto.LabUploadDTO;
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
import com.arogyavarta.console.utils.DicomToJpgConverter;
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
    public String upload(LabUploadDTO uploadDTO) throws Exception{
        Optional<Consultation> consultation = consultationRepository.findById(uploadDTO.getConsultationId());
        if (!consultation.isPresent() && consultation.get().getEndDate().isBefore(LocalDateTime.now())) {
            return "Error: consultation Id does not exist";
        }
        Lab lab = labRepository.findById(uploadDTO.getLabId()).orElseThrow();

        Tests existingTest = testsRepository.findTopByConsultationConsultationId(uploadDTO.getConsultationId());
        if (existingTest!=null && existingTest.getLab().getUserId()!=uploadDTO.getLabId()) {
            return String.format("Error: Test is already done by lab with labId: %d and name: %s",
                                 existingTest.getLab().getUserId(), existingTest.getLab().getName());
        }
        if (existingTest==null) {
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
            existingTest = testsRepository.save(test);
        }

        for (MultipartFile file : uploadDTO.getFiles()) {
            MultipartFile jpgFile = DicomToJpgConverter.convertDicomToJpg(file);
            String jpgFileName = storageUtil.uploadFile(jpgFile);
            String fileName = storageUtil.uploadFile(file);
            Images image = Images.builder().tests(existingTest).imageUrl(fileName).imageUrlJpg(jpgFileName).build();
            imagesRepository.save(image);
        }
        
        return "Uploaded";
        
    }

    public LabDTO getLabById(long id){
        Lab lab = labRepository.findById(id).orElseThrow();
        LabDTO labDTO=new LabDTO();

        labDTO.setName(lab.getName());
        labDTO.setEmail(lab.getEmail());
        labDTO.setProfilePhotoUrl(lab.getProfilePhotoUrl());
        labDTO.setContactNumber(lab.getContactNumber());
        labDTO.setAddress(lab.getAddress());
        labDTO.setAccreditationNumber(lab.getAccreditationNumber());

       return labDTO;


    }

    public void updateProfilePhoto(Long id, String filName)
    {
        Lab lab=labRepository.findById(id).orElseThrow();
        lab.setProfilePhotoUrl(filName);
        labRepository.save(lab);
    }

    public void updateLab(long id,LabDTO labDTO)
    {
        Lab lab=labRepository.findById(id).orElseThrow();


        lab.setEmail(labDTO.getEmail());
        lab.setContactNumber(labDTO.getContactNumber());
        lab.setAddress(labDTO.getAddress());


        labRepository.save(lab);

    }
    public void deleteLab(long id) {
        Optional<Lab> optionalLab = labRepository.findById(id);
        if (optionalLab.isPresent()) {
            labRepository.delete(optionalLab.get());
        } else {
            throw new RuntimeException("Lab not found with id: " + id);
        }
    }
}
