package com.arogyavarta.console.controller;


import com.arogyavarta.console.DTO.RadiologistSearchDTO;
import com.arogyavarta.console.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @GetMapping("/getAllRadiologist")
    public ResponseEntity<List<RadiologistSearchDTO>> getAllRadiologist(){
        List<RadiologistSearchDTO> radiologists=doctorService.getAllRadiologist();
        return ResponseEntity.ok(radiologists);
    }

    @PostMapping("/add/radiologist/{consultancyId}/{radiologistId}")
    public ResponseEntity<Boolean> addRadiologist(@PathVariable Long consultancyId,@PathVariable Long radiologistId){
        Boolean isAddedToConsultation=doctorService.addRadiologistToConsultation(consultancyId,radiologistId);
        return ResponseEntity.ok(isAddedToConsultation);
    }
}
