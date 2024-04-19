package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.BlacklistToken;

public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, Long>{

    BlacklistToken findByToken(String token);
    
}
