package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Consultation;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long>{
    Consultation findTopByPatientUserIdAndDoctorUserIdOrderByEndDateDesc(long patient_id, long doctor_id);
    List<Consultation> findByConsultationIdInAndEndDateBefore(List<String> consentIds, LocalDateTime currentDate);
    List<Consultation> findByConsultationIdInAndEndDateAfter(List<String> consentIds, LocalDateTime currentDate);



}
