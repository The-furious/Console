package com.arogyavarta.console.controller;

import com.arogyavarta.console.dto.RadiologistDTO;
import com.arogyavarta.console.service.RadiologistService;
import com.arogyavarta.console.utils.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RestController
@RequestMapping("/radiologist")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class RadiologistController {
    @Autowired
    private RadiologistService radiologistService;

    @Autowired
    private StorageUtil service;

    @GetMapping("/get/{id}")
    private RadiologistDTO getRadiologistById(@PathVariable long id){
        // Fetching RadiologistDTO
        RadiologistDTO radiologistDTO = radiologistService.getRadiologistById(id);

// Checking if the radiologist has a profile photo URL
        if (radiologistDTO.getProfilePhotoUrl() != null) {
            // Generating a pre-signed URL for the profile photo
            URL profilePhotoUrl = service.generatePresignedUrl(radiologistDTO.getProfilePhotoUrl());
            // Setting the generated URL back to the radiologistDTO
            radiologistDTO.setProfilePhotoUrl(profilePhotoUrl.toString());
        }

// Returning the updated radiologistDTO
        return radiologistDTO;

    }

    @PutMapping("/updateProfilePic/{id}")
    public ResponseEntity<String> updateRadiologistProfilePhoto(@PathVariable long id, @RequestParam("file") MultipartFile file ){

        String filename=service.uploadFile(file);

        radiologistService.updateProfilePhoto(id,filename);
        return ResponseEntity.ok("Radiologist updated successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRadiologist(@PathVariable long id,@RequestBody RadiologistDTO radiologistDTO)
    {
        radiologistService.updateRadiologist(id,radiologistDTO);
        return ResponseEntity.ok("Radiologist updated successfully");

    }


}
