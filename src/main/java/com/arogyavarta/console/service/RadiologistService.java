package com.arogyavarta.console.service;

import java.util.List;
import java.util.Optional;

import com.arogyavarta.console.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public RadiologistDTO getRadiologistById(Long id){

        Radiologist radiologist=radiologistRepository.findById(id).orElseThrow();
        RadiologistDTO radiologistDTO=new RadiologistDTO();

        radiologistDTO.setName(radiologist.getName());
        radiologistDTO.setEmail(radiologist.getEmail());
        radiologistDTO.setProfilePhotoUrl(radiologist.getProfilePhotoUrl());
        radiologistDTO.setContactNumber(radiologist.getContactNumber());
        radiologistDTO.setAddress(radiologist.getAddress());
        radiologistDTO.setSpecialization(radiologist.getSpecialization());
        radiologistDTO.setLicenseNumber(radiologist.getLicenseNumber());

        return radiologistDTO;





    }
    public void updateProfilePhoto(Long id, String fileName ){
        Radiologist radiologist=radiologistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Radiologist not found"));

        radiologist.setProfilePhotoUrl(fileName);




        radiologistRepository.save(radiologist);

    }

    public void updateRadiologist(long id,RadiologistDTO radiologistDTO){
        Radiologist radiologist =radiologistRepository.findById(id).orElseThrow();


        radiologist.setEmail(radiologistDTO.getEmail());
        radiologist.setContactNumber(radiologistDTO.getContactNumber());
        radiologist.setAddress(radiologistDTO.getAddress());

        radiologistRepository.save(radiologist);

    }

    public void deleteRadiologist(long id) {
        Optional<Radiologist> optionalRadiologist = radiologistRepository.findById(id);
        if (optionalRadiologist.isPresent()) {
            radiologistRepository.delete(optionalRadiologist.get());
        } else {
            throw new RuntimeException("Radiologist not found with id: " + id);
        }
    }

}
