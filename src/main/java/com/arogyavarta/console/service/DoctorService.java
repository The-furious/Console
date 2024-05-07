package com.arogyavarta.console.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.arogyavarta.console.dto.ConsentDTO;
import com.arogyavarta.console.dto.RadiologistSearchDTO;
import com.arogyavarta.console.config.Constants;
import com.arogyavarta.console.entity.*;
import com.arogyavarta.console.repo.*;
import com.arogyavarta.console.utils.EmailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.dto.DoctorDTO;

import jakarta.transaction.Transactional;
import com.arogyavarta.console.utils.StorageUtil;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsentRepository consentRepository;

    @Autowired
    private RadiologistRepository radiologistRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

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

    public List<RadiologistSearchDTO> getAllRadiologist() {
        return radiologistRepository.findAll()
                .stream()
                .map(radiologist -> new RadiologistSearchDTO(radiologist.getUserId(), radiologist.getName()))
                .collect(Collectors.toList());
    }

    public Boolean addRadiologistToConsultation(Long consultancyId, Long radiologistId) {
        Boolean isPresentConsent = consentRepository.findByConsultationIdAndUserId(consultancyId, radiologistId).isPresent();
        if (Boolean.TRUE.equals(isPresentConsent)) {
            return Boolean.FALSE;
        }
        ConsentDTO consentDTO = new ConsentDTO(LocalDateTime.now(), Boolean.FALSE, UserType.RADIOLOGIST, consultancyId, radiologistId);
        Consultation consultation = consultationRepository.findById(consultancyId).orElse(null);
        User user = userRepository.findById(consentDTO.getUserId()).orElse(null);
        if (consultation == null || user == null || user.getUserType() != UserType.RADIOLOGIST) {
            return Boolean.FALSE;
        }


        Consent consent = new Consent();
        consent.setConsultation(consultation);
        consent.setUser(user);
        consent.setGivenConsent(Boolean.FALSE);
        consent.setConsentDate(LocalDateTime.now());
        consent.setUserType(UserType.RADIOLOGIST);
        Long patientId = consultationRepository.findByConsultationId(consultancyId);
        User patient = userRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return Boolean.FALSE;
        }
        EmailUtility.sendEmail(patient.getEmail(), Constants.CONSENT_SUBJECT, Constants.CONSENT_BODY);
        consentRepository.save(consent);
        return Boolean.TRUE;
    }

    public DoctorDTO getDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow();

        DoctorDTO doctorDTO = new DoctorDTO();


        doctorDTO.setName(doctor.getName());
        doctorDTO.setEmail(doctor.getEmail());
        doctorDTO.setProfilePhotoUrl(doctor.getProfilePhotoUrl());
        doctorDTO.setContactNumber(doctor.getContactNumber());
        doctorDTO.setAddress(doctor.getAddress());
        doctorDTO.setSpecialty(doctor.getSpecialty());
        doctorDTO.setRegistrationNumber(doctor.getRegistrationNumber());


        return doctorDTO;
    }


    public void updateProfilePhoto(Long id, String filename) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        doctor.setProfilePhotoUrl(filename);


        // You can add additional logic for updating profile photo if needed
        // doctor.setProfilePhotoUrl(doctorDTO.getProfilePhotoUrl());

        doctorRepository.save(doctor);
    }

    public void updateDoctor(long id, DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow();


        doctor.setEmail(doctorDTO.getEmail());
        doctor.setContactNumber(doctorDTO.getContactNumber());
        doctor.setAddress(doctorDTO.getAddress());
        doctorRepository.save(doctor);


    }

    public void deleteDoctor(long id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            doctorRepository.delete(optionalDoctor.get());
        } else {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }
}
