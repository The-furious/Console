package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.OTP;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    
    OTP findTopByCredentialsUsernameOrderBySentDateDesc(String username);
}
