package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Consent;

public interface ConsentRepository extends JpaRepository<Consent, Long> {

}
