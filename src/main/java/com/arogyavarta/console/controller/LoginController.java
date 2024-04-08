package com.arogyavarta.console.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arogyavarta.console.dto.ChangePasswordRequest;
import com.arogyavarta.console.dto.LoginReqDTO;
import com.arogyavarta.console.dto.LoginRes;
import com.arogyavarta.console.dto.OTPRequestDTO;
import com.arogyavarta.console.service.LoginService;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginRes> authenticateAndGetToken(@RequestBody LoginReqDTO req) {
        return ResponseEntity.ok(loginService.logIn(req));
    }

    @PostMapping("/createOTP")
    public ResponseEntity<String> generateOTP(@RequestBody OTPRequestDTO otpReq) {
        loginService.generateAndSaveOTP(otpReq);
        return ResponseEntity.ok("OTP created successfully");
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<String> verifyOTP(@RequestBody OTPRequestDTO otpReq) {
        boolean isVerified = loginService.verifyOTP(otpReq);
        if (isVerified) {
            return ResponseEntity.ok("OTP Verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP or OTP expired.");
        }
    }
    @PostMapping("/changePassword")
    public ResponseEntity<String> verifyOTP(@RequestBody ChangePasswordRequest changeReq) {
        boolean changed = loginService.changePassword(changeReq);
        if (changed) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid Username or OTP expired.");
        }
    }

}
