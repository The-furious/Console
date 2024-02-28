package com.arogyavarta.console.service;

import com.arogyavarta.console.entity.Role;
import com.arogyavarta.console.entity.User;
import com.arogyavarta.console.repo.RoleRepo;
import com.arogyavarta.console.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User doctorSignUp(User user){
        Optional<User> newUser=userRepo.findById(user.getUserName());
        if(newUser.isPresent()){
            return new User();
        }
        Role doctorRole = roleRepo.findById("doctor")
                .orElseThrow(() -> new RuntimeException("Role not found with name: doctor"));
        Set<Role> roles=new HashSet<>();
        roles.add(doctorRole);
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public User adminSignUp(User user){
        Optional<User> newUser=userRepo.findById(user.getUserName());
        if(newUser.isPresent()){
            return new User();
        }
        Role adminRole = roleRepo.findById("admin")
                .orElseThrow(() -> new RuntimeException("Role not found with name: admin"));
        Set<Role> roles=new HashSet<>();
        roles.add(adminRole);
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public User radiologistSignUp(User user){
        Optional<User> newUser=userRepo.findById(user.getUserName());
        if(newUser.isPresent()){
            return new User();
        }
        Role radiologistRole = roleRepo.findById("radiologist")
                .orElseThrow(() -> new RuntimeException("Role not found with name: radiologist"));
        Set<Role> roles=new HashSet<>();
        roles.add(radiologistRole);
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public User labSignUp(User user){
        Optional<User> newUser=userRepo.findById(user.getUserName());
        if(newUser.isPresent()){
            return new User();
        }
        Role labRole = roleRepo.findById("lab_technician")
                .orElseThrow(() -> new RuntimeException("Role not found with name: lab_technician"));
        Set<Role> roles=new HashSet<>();
        roles.add(labRole);
        user.setRole(roles);
        return userRepo.save(user);
    }
    public List<User> getAllUser(){
        return userRepo.findAll();
    }
    public List<User> getAllPatient(){
        return userRepo.findByRoleRoleName("patient");
    }
    public List<User> getAllDoctor(){
        return userRepo.findByRoleRoleName("doctor");
    }
    public List<User> getAllRadiologist(){
        return userRepo.findByRoleRoleName("radiologist");
    }
    public List<User> getAllLab(){
        return userRepo.findByRoleRoleName("lab_technician");
    }
}
