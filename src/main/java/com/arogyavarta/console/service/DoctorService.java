package com.arogyavarta.console.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.DTO.DoctorDTO;
import com.arogyavarta.console.entity.Credentials;
import com.arogyavarta.console.entity.Doctor;
import com.arogyavarta.console.entity.UserType;
import com.arogyavarta.console.repo.CredentialsRepository;
import com.arogyavarta.console.repo.DoctorRepository;

import jakarta.transaction.Transactional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDTO.getName());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setProfilePhotoUrl(doctorDTO.getProfilePhotoUrl());
        doctor.setContactNumber(doctorDTO.getContactNumber());
        doctor.setAddress(doctorDTO.getAddress());
        doctor.setSpecialty(doctorDTO.getSpecialty());
        doctor.setRegistrationNumber(doctorDTO.getRegistrationNumber());
        doctorRepository.save(doctor);

        Credentials credentials = Credentials.builder()
                .user(doctor)
                .username(doctorDTO.getUsername())
                .password(passwordEncoder.encode(doctorDTO.getPassword()))
                .userType(UserType.DOCTOR)
                .build();
        credentialsRepository.save(credentials);
    }
    
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
