package com.arogyavarta.console.controller;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import com.arogyavarta.console.dto.DoctorDTO;
import com.arogyavarta.console.dto.LabDTO;
import com.arogyavarta.console.utils.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.arogyavarta.console.dto.LabHistoryDTO;
import com.arogyavarta.console.dto.LabUploadDTO;
import com.arogyavarta.console.entity.Tests;
import com.arogyavarta.console.service.LabService;
import com.arogyavarta.console.service.TestService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/lab")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class LabController {

    @Autowired
    private LabService labService;

    @Autowired
    private TestService testService;

    @Autowired
    private StorageUtil service;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(LabUploadDTO uploadDTO) throws Exception {
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

    @GetMapping("/get/{id}")
    private LabDTO getLabById(@PathVariable long id){

        LabDTO labDTO=labService.getLabById(id);

        if(labDTO.getProfilePhotoUrl()!=null){
            URL profilePhotoUrl=service.generatePresignedUrl(labDTO.getProfilePhotoUrl());
            labDTO.setProfilePhotoUrl(profilePhotoUrl.toString());


        }

        return labDTO;

    }


    @PutMapping("/updateProfilePic/{id}")
    public ResponseEntity<String> updateLabPhoto(@PathVariable long id, @RequestParam("file") MultipartFile file ){

        String filename=service.uploadFile(file);

        labService.updateProfilePhoto(id,filename);
        return ResponseEntity.ok("Lab updated successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRadiologist(@PathVariable long id,@RequestBody LabDTO labDTO)
    {
        labService.updateLab(id, labDTO);

        return ResponseEntity.ok("Lab updated successfully");


    }






}
