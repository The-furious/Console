package com.arogyavarta.console.service;

import com.arogyavarta.console.entity.Role;
import com.arogyavarta.console.entity.User;
import com.arogyavarta.console.repo.RoleRepo;
import com.arogyavarta.console.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PatientService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User patientSignup(User user){
        Optional<User> newUser=userRepo.findById(user.getUserName());
        if(newUser.isPresent()){
            return new User();
        }
        Role patientRole = roleRepo.findById("patient")
                .orElseThrow(() -> new RuntimeException("Role not found with name: patient"));
        Set<Role> roles=new HashSet<>();
        roles.add(patientRole);
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
