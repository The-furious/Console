package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
}