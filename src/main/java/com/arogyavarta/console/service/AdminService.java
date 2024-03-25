package com.arogyavarta.console.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.DTO.AdminDTO;
import com.arogyavarta.console.entity.Admin;
import com.arogyavarta.console.entity.Credentials;
import com.arogyavarta.console.entity.Role;
import com.arogyavarta.console.entity.UserLogin;
import com.arogyavarta.console.entity.UserType;
import com.arogyavarta.console.repo.AdminRepository;
import com.arogyavarta.console.repo.CredentialsRepository;
import com.arogyavarta.console.repo.RoleRepo;
import com.arogyavarta.console.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class AdminService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

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
                .password(adminDTO.getPassword())
                .userType(UserType.ADMIN)
                .build();
        credentialsRepository.save(credentials);
    }
    public UserLogin doctorSignUp(UserLogin user){
        Optional<UserLogin> newUser=userRepo.findById(user.getUserName());
        if(newUser.isPresent()){
            return new UserLogin();
        }
        Role doctorRole = roleRepo.findById("doctor")
                .orElseThrow(() -> new RuntimeException("Role not found with name: doctor"));
        Set<Role> roles=new HashSet<>();
        roles.add(doctorRole);
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public UserLogin adminSignUp(UserLogin user){
        Optional<UserLogin> newUser=userRepo.findById(user.getUserName());
        if(newUser.isPresent()){
            return new UserLogin();
        }
        Role adminRole = roleRepo.findById("admin")
                .orElseThrow(() -> new RuntimeException("Role not found with name: admin"));
        Set<Role> roles=new HashSet<>();
        roles.add(adminRole);
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public UserLogin radiologistSignUp(UserLogin user){
        Optional<UserLogin> newUser=userRepo.findById(user.getUserName());
        if(newUser.isPresent()){
            return new UserLogin();
        }
        Role radiologistRole = roleRepo.findById("radiologist")
                .orElseThrow(() -> new RuntimeException("Role not found with name: radiologist"));
        Set<Role> roles=new HashSet<>();
        roles.add(radiologistRole);
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public UserLogin labSignUp(UserLogin user){
        Optional<UserLogin> newUser=userRepo.findById(user.getUserName());
        if(newUser.isPresent()){
            return new UserLogin();
        }
        Role labRole = roleRepo.findById("lab_technician")
                .orElseThrow(() -> new RuntimeException("Role not found with name: lab_technician"));
        Set<Role> roles=new HashSet<>();
        roles.add(labRole);
        user.setRole(roles);
        return userRepo.save(user);
    }
    public List<UserLogin> getAllUser(){
        return userRepo.findAll();
    }
    public List<UserLogin> getAllPatient(){
        return userRepo.findByRoleRoleName("patient");
    }
    public List<UserLogin> getAllDoctor(){
        return userRepo.findByRoleRoleName("doctor");
    }
    public List<UserLogin> getAllRadiologist(){
        return userRepo.findByRoleRoleName("radiologist");
    }
    public List<UserLogin> getAllLab(){
        return userRepo.findByRoleRoleName("lab_technician");
    }
}
