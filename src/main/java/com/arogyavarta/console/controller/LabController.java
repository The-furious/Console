package com.arogyavarta.console.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arogyavarta.console.dto.LabHistoryDTO;
import com.arogyavarta.console.dto.LabUploadDTO;
import com.arogyavarta.console.entity.Tests;
import com.arogyavarta.console.service.LabService;
import com.arogyavarta.console.service.TestService;

@RestController
@RequestMapping("/lab")
public class LabController {

    @Autowired
    private LabService labService;

    @Autowired
    private TestService testService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(LabUploadDTO uploadDTO) {
        String res = labService.upload(uploadDTO);
        if (!res.contains("Error")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/history/{labId}")
    public List<LabHistoryDTO> getLabHistoryForLabId(@PathVariable Long labId) {
        List<Tests> tests = testService.findAllTestsForLabId(labId);
        return tests.stream()
                .map(this::convertTestToLabHistoryDTO)
                .collect(Collectors.toList());
    }

    private LabHistoryDTO convertTestToLabHistoryDTO(Tests test) {
        return LabHistoryDTO.builder()
                .testName(test.getTestName())
                .remarks(test.getRemarks())
                .testDate(test.getTestDate())
                .consultationId(test.getConsultation().getConsultationId())
                .patientName(test.getConsultation().getPatient().getName()) 
                .build();
    }

}
