package com.arogyavarta.console.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arogyavarta.console.DTO.ConsultationDTO;
import com.arogyavarta.console.entity.Consultation;
import com.arogyavarta.console.service.ConsultationService;

@RestController
@RequestMapping("/consultation")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;

    @PostMapping("/create")
    public ResponseEntity<Consultation> createConsultation(@RequestBody ConsultationDTO consultationDTO) {
        Consultation consultation =  consultationService.createConsultation(consultationDTO);
        return ResponseEntity.ok(consultation);
    }
}