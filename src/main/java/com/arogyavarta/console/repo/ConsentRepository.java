package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Consent;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsentRepository extends JpaRepository<Consent, Long> {
    @Query(value = "SELECT consultation_id FROM consent WHERE given_consent = TRUE AND user_id = ?1", nativeQuery = true)
    List<String> findConsultationIdsByConsentAndUserId(Long userId);
}
