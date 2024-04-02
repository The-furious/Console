package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Tests;

public interface TestsRepository extends JpaRepository<Tests, Long>{
    
}
