package com.arogyavarta.console.controller;

import com.arogyavarta.console.entity.User;
import com.arogyavarta.console.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @PostMapping("/signup")
    public User signUp(@RequestBody User user){
        return patientService.patientSignup(user);
    }
}
