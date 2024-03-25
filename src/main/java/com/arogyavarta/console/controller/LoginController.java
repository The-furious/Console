package com.arogyavarta.console.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arogyavarta.console.DTO.LoginReqDTO;
import com.arogyavarta.console.DTO.LoginRes;
import com.arogyavarta.console.service.LoginService;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginRes> AuthenticateAndGetToken(@RequestBody LoginReqDTO req) {
        return ResponseEntity.ok(loginService.logIn(req));
    }
}
