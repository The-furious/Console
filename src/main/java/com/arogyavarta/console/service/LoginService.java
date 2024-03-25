package com.arogyavarta.console.service;

import com.arogyavarta.console.DTO.AuthBodyDTO;
import com.arogyavarta.console.entity.Role;
import com.arogyavarta.console.entity.UserLogin;
import com.arogyavarta.console.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class LoginService {
    @Autowired
    private  UserRepo userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    public  Boolean isSameRole(AuthBodyDTO authBodyDTO){
        Optional<UserLogin> userO= userRepository.findById(authBodyDTO.getUsername());
        if(userO.isEmpty()){
            return false;
        }
        Set<Role> userRoles = userO.get().getRole();
        for (Role role : userRoles) {
            if (role.getRoleName().equals(authBodyDTO.getRole())) {
                return true;
            }
        }
        return false;
    }
}
