package com.arogyavarta.console.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arogyavarta.console.DTO.ConsultationDTO;
import com.arogyavarta.console.DTO.TestReportDTO;
import com.arogyavarta.console.entity.Consultation;
import com.arogyavarta.console.service.ConsultationService;
import com.arogyavarta.console.service.TestService;


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
}