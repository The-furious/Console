package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long>{

    Prescription findByConsultation_ConsultationId(Long consultationId);
} 