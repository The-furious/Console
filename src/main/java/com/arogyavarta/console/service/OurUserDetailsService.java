package com.arogyavarta.console.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.repo.CredentialsRepository;

@Service
public class OurUserDetailsService implements UserDetailsService {

    @Autowired
    private CredentialsRepository cRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return cRepository.findByUsername(username).orElseThrow();
    }
}
