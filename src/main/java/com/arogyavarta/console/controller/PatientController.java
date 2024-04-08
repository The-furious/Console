package com.arogyavarta.console.controller;

import com.arogyavarta.console.dto.NonConsentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arogyavarta.console.dto.PatientDTO;
import com.arogyavarta.console.entity.Patient;
import com.arogyavarta.console.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/createPatient")
    public ResponseEntity<String> createPatient(@RequestBody PatientDTO patientDTO) {
        patientService.createPatient(patientDTO);
        return ResponseEntity.ok("Patient created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") Long id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }
    @GetMapping("/notification/non-consents/{userId}")
    public ResponseEntity<List<NonConsentDTO>> getAllNonConsents(@PathVariable Long userId){
        List<NonConsentDTO> nonConsentDTO=patientService.getAllUngivenConsents(userId);
        return ResponseEntity.ok(nonConsentDTO);
    }
}
