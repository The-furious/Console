package com.arogyavarta.console.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
