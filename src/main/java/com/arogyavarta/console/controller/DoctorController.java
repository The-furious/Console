package com.arogyavarta.console.controller;


import com.arogyavarta.console.dto.DoctorDTO;
import com.arogyavarta.console.dto.LabDTO;
import com.arogyavarta.console.dto.RadiologistSearchDTO;
import com.arogyavarta.console.entity.Doctor;
import com.arogyavarta.console.service.DoctorService;
import com.arogyavarta.console.utils.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private StorageUtil service;
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
    @GetMapping("/get/{id}")
    private DoctorDTO getLabById(@PathVariable long id){

        DoctorDTO doctorDTO=doctorService.getDoctorById(id);

        if(doctorDTO.getProfilePhotoUrl()!=null){
            URL profilePhotoUrl=service.generatePresignedUrl(doctorDTO.getProfilePhotoUrl());
            doctorDTO.setProfilePhotoUrl(profilePhotoUrl.toString());


        }

        return doctorDTO;

    }
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();

        for (Doctor doctor : doctors) {
            if (doctor.getProfilePhotoUrl() != null) {
                URL profilePhotoUrl = service.generatePresignedUrl(doctor.getProfilePhotoUrl());
                doctor.setProfilePhotoUrl(profilePhotoUrl.toString());
            }
        }

        return doctors;
    }

    @PutMapping("/updateProfilePic/{id}")
    public ResponseEntity<String> updateDoctorPhoto(@PathVariable long id, @RequestParam("file") MultipartFile file ){

        String filename=service.uploadFile(file);

        doctorService.updateProfilePhoto(id,filename);
        return ResponseEntity.ok("Doctor updated successfully");
    }

    @PutMapping("update/{id}")
    public  ResponseEntity<String> updateDoctor(@PathVariable long id,@RequestBody DoctorDTO doctorDTO)
    {
        doctorService.updateDoctor(id,doctorDTO);
        return ResponseEntity.ok("Doctor updated successfully");
    }

}
