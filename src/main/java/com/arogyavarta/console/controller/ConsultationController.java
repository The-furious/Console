package com.arogyavarta.console.controller;

import com.arogyavarta.console.DTO.ConsentNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.arogyavarta.console.DTO.ConsultationDTO;
import com.arogyavarta.console.DTO.TestReportDTO;
import com.arogyavarta.console.entity.Consultation;
import com.arogyavarta.console.service.ConsultationService;
import com.arogyavarta.console.service.TestService;


import java.util.List;

@RestController
@RequestMapping("/consultation")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private TestService testsService;

    @PostMapping("/create")
    public ResponseEntity<Consultation> createConsultation(@RequestBody ConsultationDTO consultationDTO) {
        Consultation consultation =  consultationService.createConsultation(consultationDTO);
        return ResponseEntity.ok(consultation);
    }

    @GetMapping("/labReport/{consultationId}")
    public ResponseEntity<TestReportDTO> labReport(@RequestParam Long consultationId) {
        TestReportDTO reports =  testsService.getTestReports(consultationId);
        return ResponseEntity.ok(reports);
    }
  
    @GetMapping("/get/history/{userId}")
    public ResponseEntity<List<Consultation>> getConsultationHistory(@PathVariable Long userId){
        List<Consultation> previousConsultation=consultationService.getConsultationHistory(userId);
        return ResponseEntity.ok(previousConsultation);
    }
  
    @GetMapping("/get/present/{userId}")
    public ResponseEntity<List<Consultation>> getConsultationPresent(@PathVariable Long userId){
        List<Consultation> previousConsultation=consultationService.getConsultationPresent(userId);
        return ResponseEntity.ok(previousConsultation);
    }


    @GetMapping("/{consultationId}")
    public ResponseEntity<List<ConsentNameDTO>> getGivenConsent(@PathVariable Long consultationId){
        List<ConsentNameDTO> givenconsent = consultationService.getGivenConsent(consultationId);
        return ResponseEntity.ok(givenconsent);
    }
}