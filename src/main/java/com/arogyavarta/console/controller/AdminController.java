package com.arogyavarta.console.controller;

import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.arogyavarta.console.utils.StorageUtil;

@RestController
@RequestMapping("/admin")
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
        return doctorService.getAllDoctors();
    }

    @GetMapping("/getAllLabs")
    public List<Lab> getAllLab() {
        return labService.getAllLabs();
    }

    @GetMapping("/getAllRadiologist")
    public List<Radiologist> getAllRadiologist() {
        return radiologistService.getAllRadiologists();
    }

}
