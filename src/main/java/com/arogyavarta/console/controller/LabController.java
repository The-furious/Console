package com.arogyavarta.console.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arogyavarta.console.DTO.LabUploadDTO;
import com.arogyavarta.console.service.LabService;

@RestController
@RequestMapping("/lab")
public class LabController {

    @Autowired
    private LabService labService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(LabUploadDTO uploadDTO) {
        String res = labService.upload(uploadDTO);
        if (!res.contains("Error")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }
}
