package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Consent;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ConsentRepository extends JpaRepository<Consent, Long> {
    @Query(value = "SELECT consultation_id FROM consent WHERE given_consent = TRUE AND user_id = ?1", nativeQuery = true)
    List<String> findConsultationIdsByConsentAndUserId(Long userId);
    @Query(value = "SELECT * FROM consent WHERE consultation_id = ?1 AND user_id = ?2", nativeQuery = true)
    Optional<Consent> findByConsultationIdAndUserId(Long consultancyId, Long userId);
    @Query(value = "SELECT * FROM consent WHERE consultation_id=?1", nativeQuery = true)
    List<Consent> findAllByConsultationId(Long consultationId);
    @Query(value = "SELECT * FROM consent WHERE consultation_id=?1 and user_id!=?2", nativeQuery = true)
    List<Consent> findAllByUserIdAndConsultationIdNotDoctor(Long consultationId, Long userId);
    @Query(value = "SELECT * FROM consent WHERE consultation_id In ?2 and given_consent=?1", nativeQuery = true)
    List<Consent> findByGivenConsentAndUserIdIn(int i, Set<Long> userIds);
}
