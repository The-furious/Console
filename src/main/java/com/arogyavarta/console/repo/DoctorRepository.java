package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}