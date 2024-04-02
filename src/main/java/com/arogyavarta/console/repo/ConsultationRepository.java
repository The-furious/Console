package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long>{
    Consultation findTopByPatientUserIdAndDoctorUserIdOrderByEndDateDesc(long patient_id, long doctor_id);
}
