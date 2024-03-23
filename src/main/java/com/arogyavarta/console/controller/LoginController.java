package com.arogyavarta.console.controller;

import com.arogyavarta.console.DTO.AuthBodyDTO;
import com.arogyavarta.console.DTO.AuthRequestDTO;
import com.arogyavarta.console.DTO.JwtResponseDTO;
import com.arogyavarta.console.service.JwtService;
import com.arogyavarta.console.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthBodyDTO authBodyDTO) {
        if (loginService.isSameRole(authBodyDTO)) {
            AuthRequestDTO authRequestDTO = new AuthRequestDTO();
            authRequestDTO.setUsername(authBodyDTO.getUsername());
            authRequestDTO.setPassword(authBodyDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                return JwtResponseDTO.builder()
                        .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername())).build();
            } else {
                throw new UsernameNotFoundException("invalid user request..!!");
            }
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
}
