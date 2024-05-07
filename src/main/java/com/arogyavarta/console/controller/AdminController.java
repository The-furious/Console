package com.arogyavarta.console.controller;

import java.net.URL;
import java.util.List;

import com.arogyavarta.console.utils.StorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.arogyavarta.console.dto.AdminDTO;
import com.arogyavarta.console.dto.DoctorDTO;
import com.arogyavarta.console.dto.LabDTO;
import com.arogyavarta.console.dto.RadiologistDTO;
import com.arogyavarta.console.entity.Doctor;
import com.arogyavarta.console.entity.Lab;
import com.arogyavarta.console.entity.Radiologist;
import com.arogyavarta.console.service.AdminService;
import com.arogyavarta.console.service.DoctorService;
import com.arogyavarta.console.service.LabService;
import com.arogyavarta.console.service.RadiologistService;
import com.arogyavarta.console.utils.EmailUtility;
import org.springframework.web.multipart.MultipartFile;
import com.arogyavarta.console.utils.StorageUtil;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private LabService labService;
    @Autowired
    private RadiologistService radiologistService;
    @Autowired
    private StorageUtil storageUtil;

    @Autowired
    private StorageUtil service;

    // @GetMapping("/getAllUser")
    // public ResponseEntity<List<UserLogin>> getAllUser(){
    //     return new ResponseEntity<>(adminService.getAllUser(),HttpStatus.OK);
    // }

    @GetMapping("/sayHello")
    public void sayHello() throws Exception{
        //PDFGenerator.generateReport("report.pdf");
        //return storageUtil.generatePresignedUrl("1714666807958_0002.DCM");
        //return storageUtil.generatePresignedUrl("1712419201194_download.jpeg.DCM");
    }

    @GetMapping("/sayHello1")
    public URL sayHello1() throws Exception{
        return storageUtil.generatePresignedUrl("1711796322190_download.jpeg");
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<String> createAdmin(@RequestBody AdminDTO adminDTO) {
        adminService.createAdmin(adminDTO);
        return ResponseEntity.ok("Admin created successfully");
    }
    @PostMapping("/createDoctor")
    public ResponseEntity<String> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        doctorService.createDoctor(doctorDTO);
        return ResponseEntity.ok("Doctor created successfully");
    }

    @PostMapping("/createLab")
    public ResponseEntity<String> createLab(@RequestBody LabDTO labDTO) {
        labService.createLab(labDTO);
        return ResponseEntity.ok("Lab created successfully");
    }

    @PostMapping("/createRadiologist")
    public ResponseEntity<String> createRadiologist(@RequestBody RadiologistDTO radiologistDTO) {
        radiologistService.createRadiologist(radiologistDTO);
        return ResponseEntity.ok("Radiologist created successfully");
    }

    @GetMapping("/getAllDoctors")
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();

        for (Doctor doctor : doctors) {
            if (doctor.getProfilePhotoUrl() != null) {
                URL profilePhotoUrl = service.generatePresignedUrl(doctor.getProfilePhotoUrl());
                doctor.setProfilePhotoUrl(profilePhotoUrl.toString());
            }
            else{
                doctor.setProfilePhotoUrl("https://bootdey.com/img/Content/avatar/avatar1.png");
            }
        }

        return doctors;
    }

    @GetMapping("/getAllLabs")
    public List<Lab> getAllLabs() {
        List<Lab> labs = labService.getAllLabs();

        for (Lab lab : labs) {
            if (lab.getProfilePhotoUrl() != null) {
                URL profilePhotoUrl = service.generatePresignedUrl(lab.getProfilePhotoUrl());
                lab.setProfilePhotoUrl(profilePhotoUrl.toString());
            }
            else{
                lab.setProfilePhotoUrl("https://bootdey.com/img/Content/avatar/avatar1.png");
            }
        }

        return labs;
    }


    @GetMapping("/getAllRadiologist")
    public List<Radiologist> getAllRadiologists() {
        List<Radiologist> radiologists = radiologistService.getAllRadiologists();

        for (Radiologist radiologist : radiologists) {
            if (radiologist.getProfilePhotoUrl() != null) {
                URL profilePhotoUrl = service.generatePresignedUrl(radiologist.getProfilePhotoUrl());
                radiologist.setProfilePhotoUrl(profilePhotoUrl.toString());
            }
            else{
                radiologist.setProfilePhotoUrl("https://bootdey.com/img/Content/avatar/avatar1.png");
            }
        }

        return radiologists;
    }

    @GetMapping("/get/{id}")
    public AdminDTO getAdminById(@PathVariable long id){

        AdminDTO adminDTO=adminService.getAdminById(id);

        if(adminDTO.getProfilePhotoUrl()!=null){
            URL profilePhotoUrl=service.generatePresignedUrl(adminDTO.getProfilePhotoUrl());
            adminDTO.setProfilePhotoUrl(profilePhotoUrl.toString());


        }

        return adminDTO;

    }

    @PutMapping("/updateProfilePic/{id}")
    public ResponseEntity<String> updateAdminProfilePhoto(@PathVariable long id, @RequestParam("file") MultipartFile file ){

        String filename=service.uploadFile(file);

        adminService.updateProfilePhoto(id,filename);
        return ResponseEntity.ok("Admin updated successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAdmin(@PathVariable long id,@RequestBody AdminDTO adminDTO)
    {
        adminService.updateAdmin(id,adminDTO);
        return ResponseEntity.ok("Admin updated successfully");

    }
    @PutMapping("/updateDoctor/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable long id,@RequestBody DoctorDTO doctorDTO)
    {
        doctorService.updateDoctor(id,doctorDTO);
        return ResponseEntity.ok("Doctor updated successfully");

    }

    @PutMapping("/updateRadiologist/{id}")
    public ResponseEntity<String> updateRadiologist(@PathVariable long id,@RequestBody RadiologistDTO radiologistDTO)
    {
       radiologistService.updateRadiologist(id,radiologistDTO);
        return ResponseEntity.ok("Radiologist updated successfully");

    }

    @PutMapping("/updateLab/{id}")
    public ResponseEntity<String> updateLab(@PathVariable long id,@RequestBody LabDTO labDTO)
    {
        labService.updateLab(id,labDTO);
        return ResponseEntity.ok("Lab updated successfully");

    }

    @DeleteMapping("/deleteDoctor/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }

    @DeleteMapping("/deleteRadiologist/{id}")
    public ResponseEntity<String> deleteRadiologist(@PathVariable long id) {
        radiologistService.deleteRadiologist(id);
        return ResponseEntity.ok("Radiologist deleted successfully");
    }

    @DeleteMapping("/deleteLab/{id}")
    public ResponseEntity<String> deleteLab(@PathVariable long id) {
        labService.deleteLab(id);
        return ResponseEntity.ok("Lab deleted successfully");
    }




}
