package com.arogyavarta.console.repo;


import com.arogyavarta.console.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface RoleRepo extends JpaRepository<Role,String> {
}
