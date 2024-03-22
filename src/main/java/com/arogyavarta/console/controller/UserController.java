//package com.arogyavarta.console.controller;
//
//import com.arogyavarta.console.entity.User;
//import com.arogyavarta.console.service.AdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class UserController {
//    @Autowired
//    private AdminService adminService;
////    @PostMapping("/patient/signup")
////    public ResponseEntity<User> patientSignUp(@RequestBody User user){
////        User newUser = adminService.patientSignUp(user);
////        if(newUser.getUserName()==null) return new ResponseEntity<>(user, HttpStatus.CONFLICT);
////        return new ResponseEntity<>(newUser, HttpStatus.OK);
////    }
//    @PostMapping("/doctor/signup")
//    public ResponseEntity<User> doctorSignUp(@RequestBody User user){
//        User newUser = adminService.doctorSignUp(user);
//        if(newUser.getUserName()==null) return new ResponseEntity<>(user, HttpStatus.CONFLICT);
//        return new ResponseEntity<>(newUser, HttpStatus.OK);
//    }
//
//    @GetMapping("/getAllUser")
//    public ResponseEntity<List<User>> getAllUser(){
//        return new ResponseEntity<>(adminService.getAllUser(),HttpStatus.OK);
//    }
//    @GetMapping("/getAllPatientUser")
//    public ResponseEntity<List<User>> getAllPatientUser(){
//        return new ResponseEntity<>(adminService.getAllPatient(),HttpStatus.OK);
//    }
//}
