package com.arogyavarta.console.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arogyavarta.console.DTO.AdminDTO;
import com.arogyavarta.console.DTO.DoctorDTO;
import com.arogyavarta.console.DTO.LabDTO;
import com.arogyavarta.console.DTO.RadiologistDTO;
import com.arogyavarta.console.entity.UserLogin;
import com.arogyavarta.console.service.AdminService;
import com.arogyavarta.console.service.DoctorService;
import com.arogyavarta.console.service.LabService;
import com.arogyavarta.console.service.RadiologistService;

@RestController
@RequestMapping("/admin")
public class AdminController {
   @GetMapping("/sayHello")
   public String sayHello(){
       return "Hello World!";
   }
    @Autowired
    private AdminService adminService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private LabService labService;
    @Autowired
    private RadiologistService radiologistService;

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserLogin>> getAllUser(){
        return new ResponseEntity<>(adminService.getAllUser(),HttpStatus.OK);
    }
    @GetMapping("/getAllPatient")
    public ResponseEntity<List<UserLogin>> getAllPatient(){
        return new ResponseEntity<>(adminService.getAllPatient(),HttpStatus.OK);
    }
    @GetMapping("/getAllDoctor")
    public ResponseEntity<List<UserLogin>> getAllDoctor(){
        return new ResponseEntity<>(adminService.getAllDoctor(),HttpStatus.OK);
    }
    @GetMapping("/getAllRadiologist")
    public ResponseEntity<List<UserLogin>> getAllRadiologist(){
        return new ResponseEntity<>(adminService.getAllRadiologist(),HttpStatus.OK);
    }
    @GetMapping("/getAllLab")
    public ResponseEntity<List<UserLogin>> getAllLab(){
        return new ResponseEntity<>(adminService.getAllLab(),HttpStatus.OK);
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

}
