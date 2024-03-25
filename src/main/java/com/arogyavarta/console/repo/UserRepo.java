package com.arogyavarta.console.repo;

import com.arogyavarta.console.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;


@EnableJpaRepositories
public interface UserRepo extends JpaRepository<UserLogin,String> {
    List<UserLogin> findByRoleRoleName(String roleName);
}
