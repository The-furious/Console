package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
