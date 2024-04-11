package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.ActiveUser;
import com.arogyavarta.console.entity.User;

public interface ActiveUserRepository extends JpaRepository<ActiveUser, User> {
    
    ActiveUser findByUserUserId(Long userId);
}
