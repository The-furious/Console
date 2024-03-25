package com.arogyavarta.console.service;

import com.arogyavarta.console.DTO.LoginReqDTO;
import com.arogyavarta.console.config.CustomUserDetails;
import com.arogyavarta.console.entity.UserLogin;
import com.arogyavarta.console.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Entering in loadUserByUsername Method...");
        Optional<UserLogin> optionalUser = userRepository.findById(username);
        logger.info("Optional User: "+optionalUser+" "+optionalUser.get().getRole());
        UserLogin user=optionalUser.orElse(null);
        if(user == null){
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        logger.info("User Authenticated Successfully..!!!");
        logger.info("User: "+user);
        return new CustomUserDetails(user);
    }

}