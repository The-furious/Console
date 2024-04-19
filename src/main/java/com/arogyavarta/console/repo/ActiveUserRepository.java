package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.ActiveUser;

public interface ActiveUserRepository extends JpaRepository<ActiveUser, Long> {
    
    ActiveUser findByUserUserId(Long userId);
}
