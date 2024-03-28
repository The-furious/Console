package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Radiologist;

public interface RadiologistRepository extends JpaRepository<Radiologist, Long>{
    
}
