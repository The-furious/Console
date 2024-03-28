package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
