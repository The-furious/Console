package com.arogyavarta.console.controller;

import java.net.URL;
import java.util.List;

import com.arogyavarta.console.dto.DoctorDTO;
import com.arogyavarta.console.utils.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.arogyavarta.console.dto.NonConsentDTO;
import com.arogyavarta.console.dto.PatientDTO;
import com.arogyavarta.console.entity.Patient;
import com.arogyavarta.console.service.PatientService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/patient")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private StorageUtil service;

    @PostMapping("/createPatient")
    public ResponseEntity<String> createPatient(@RequestBody PatientDTO patientDTO) throws Exception{
        patientService.createPatient(patientDTO);
        return ResponseEntity.ok("Patient created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") Long id) throws Exception{
        Patient patient=patientService.getPatientById(id);

        if(patient.getProfilePhotoUrl()!=null){
            URL profilePhotoUrl=service.generatePresignedUrl(patient.getProfilePhotoUrl());
            patient.setProfilePhotoUrl(profilePhotoUrl.toString());


        }
        return ResponseEntity.ok(patient);
    }
    @GetMapping("/notification/non-consents/{userId}")
    public ResponseEntity<List<NonConsentDTO>> getAllNonConsents(@PathVariable Long userId){
        List<NonConsentDTO> nonConsentDTO=patientService.getAllUngivenConsents(userId);
        return ResponseEntity.ok(nonConsentDTO);
    }

    @PutMapping("/updateProfilePic/{id}")
    public ResponseEntity<String> updatePatientProfilePhoto(@PathVariable long id, @RequestParam("file") MultipartFile file ){

        String filename=service.uploadFile(file);

        patientService.updateProfilePhoto(id,filename);
        return ResponseEntity.ok("Radiologist updated successfully");
    }

}
