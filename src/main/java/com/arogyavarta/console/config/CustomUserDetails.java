package com.arogyavarta.console.config;

import com.arogyavarta.console.entity.Role;
import com.arogyavarta.console.entity.UserLogin;
import com.arogyavarta.console.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CustomUserDetails extends UserLogin implements UserDetails {

    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    public CustomUserDetails(UserLogin byUsername) {
    logger.info("Here");
        this.username = byUsername.getUserName();
        this.password= byUsername.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();
        logger.info("roles " +byUsername.getRole());
        for(Role role : byUsername.getRole()){

            auths.add(new SimpleGrantedAuthority(role.getRoleName().toLowerCase()));
        }
        this.authorities = auths;
        logger.info("auths " +auths);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        logger.info("Autho "+authorities);return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}