package com.arogyavarta.console.controller;

import com.arogyavarta.console.dto.ConsentNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.arogyavarta.console.dto.ConsultationDTO;
import com.arogyavarta.console.dto.TestReportDTO;
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
    public ResponseEntity<TestReportDTO> labReport(@PathVariable Long consultationId) {
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


    @GetMapping("/{consultationId}/{userId}")
    public ResponseEntity<List<ConsentNameDTO>> getGivenConsent(@PathVariable Long consultationId,@PathVariable Long userId){
        List<ConsentNameDTO> givenconsent = consultationService.getGivenConsent(consultationId,userId);
        return ResponseEntity.ok(givenconsent);
    }
}