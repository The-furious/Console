package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Lab;

public interface LabRepository extends JpaRepository<Lab, Long> {
}