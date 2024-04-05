package com.arogyavarta.console.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Tests;

public interface TestsRepository extends JpaRepository<Tests, Long>{
    
    List<Tests> findByLabUserId(Long labId);

    Tests findTopByConsultationConsultationId(Long consultationId);
}
