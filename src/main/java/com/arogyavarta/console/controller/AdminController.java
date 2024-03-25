package com.arogyavarta.console.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.arogyavarta.console.entity.UserLogin;
import com.arogyavarta.console.service.AdminService;
import com.arogyavarta.console.service.DoctorService;
import com.arogyavarta.console.service.LabService;
import com.arogyavarta.console.service.RoleService;
import com.arogyavarta.console.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/admin")
public class AdminController {
//    @GetMapping("/sayHello")
//    @PermitAll
//    public String sayHello(){
//        return "Hello World!";
//    }
    @Autowired
    private RoleService roleService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private LabService labService;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
//    @PreAuthorize("hasRole('admin')")
    @PostMapping("/doctor/signup")
    public ResponseEntity<UserLogin> doctorSignUp(@RequestBody UserLogin user){
        UserLogin newUser = adminService.doctorSignUp(user);
        if(newUser.getUserName()==null) return new ResponseEntity<>(user, HttpStatus.CONFLICT);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
//    @PostMapping("/admin/signup")
//    public ResponseEntity<User> adminSignUp(@RequestBody User user){
//        User newUser = adminService.adminSignUp(user);
//        if(newUser.getUserName()==null) return new ResponseEntity<>(user, HttpStatus.CONFLICT);
//        return new ResponseEntity<>(newUser, HttpStatus.OK);
//    }
    @PostMapping("/radiologist/signup")
    public ResponseEntity<UserLogin> radiologistSignUp(@RequestBody UserLogin user){
        UserLogin newUser = adminService.radiologistSignUp(user);
        if(newUser.getUserName()==null) return new ResponseEntity<>(user, HttpStatus.CONFLICT);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
    @PostMapping("/lab/signup")
    public ResponseEntity<UserLogin> labSignUp(@RequestBody UserLogin user){
        UserLogin newUser = adminService.labSignUp(user);
        if(newUser.getUserName()==null) return new ResponseEntity<>(user, HttpStatus.CONFLICT);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

//    @PostMapping("/createNewRole")
//    public ResponseEntity<Role>  createNewRole(@RequestBody Role role){
//        Role newRole=roleService.createNewRole(role);
//        return new ResponseEntity<>(newRole, HttpStatus.OK);
//    }
//    @PreAuthorize("hasAuthority('admin')")
//    @GetMapping("/getAllRoles")
//    public ResponseEntity<List<Role>> getAllRoles(){
//        return new ResponseEntity<>(roleService.getALLRoles(),HttpStatus.OK);
//    }
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

}
