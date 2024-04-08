package com.arogyavarta.console.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.dto.AdminDTO;
import com.arogyavarta.console.entity.Admin;
import com.arogyavarta.console.entity.Credentials;
import com.arogyavarta.console.entity.UserType;
import com.arogyavarta.console.repo.AdminRepository;
import com.arogyavarta.console.repo.CredentialsRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
        admin.setProfilePhotoUrl(adminDTO.getProfilePhotoUrl());
        admin.setContactNumber(adminDTO.getContactNumber());
        admin.setAddress(adminDTO.getAddress());
        admin.setJoinDate(new Date()); 

        adminRepository.save(admin);

        Credentials credentials = Credentials.builder()
                .user(admin)
                .username(adminDTO.getUsername())
                .password(passwordEncoder.encode(adminDTO.getPassword()))
                .userType(UserType.ADMIN)
                .build();
        credentialsRepository.save(credentials);
    }
}
