package com.arogyavarta.console.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.arogyavarta.console.DTO.ConsultationDTO;
import com.arogyavarta.console.entity.Consultation;
import com.arogyavarta.console.service.ConsultationService;

import java.util.List;

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
}