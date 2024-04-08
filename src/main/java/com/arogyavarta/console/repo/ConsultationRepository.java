package com.arogyavarta.console.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Consultation;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long>{
    Consultation findTopByPatientUserIdAndDoctorUserIdOrderByEndDateDesc(long patientId, long doctorId);
    List<Consultation> findByConsultationIdInAndEndDateBefore(List<String> consentIds, LocalDateTime currentDate);
    List<Consultation> findByConsultationIdInAndEndDateAfter(List<String> consentIds, LocalDateTime currentDate);
    @Query(value = "SELECT patient_id FROM consultation WHERE consultation_id = ?1", nativeQuery = true)
    Long findByConsultationId(Long consultancyId);

    List<Consultation> findAllDoctorByConsultationId(Long consultationId);
    @Query(value = "SELECT * FROM consultation WHERE patient_id = ?1", nativeQuery = true)
    List<Consultation> findAllByPatientId(Long userId);
}
