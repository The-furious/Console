package com.arogyavarta.console.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.arogyavarta.console.service.CredentialsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private JwtAuthFilter jwtAuthFIlter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request.requestMatchers("/auth/**", "/public/**", "/swagger-ui/**", "/v3/**", "/patient/createPatient", "/**").permitAll()
                        .requestMatchers("/auth/logout/**").hasAnyAuthority("DOCTOR", "RADIOLOGIST", "PATIENT", "ADMIN")
                        .requestMatchers("/admin/getAllDoctors/**", "/admin/getAllRadiologist/**", "/patient/{id}/**").hasAnyAuthority("DOCTOR", "RADIOLOGIST", "ADMIN", "PATIENT")
                        .requestMatchers("/wss/**").hasAnyAuthority("DOCTOR", "RADIOLOGIST", "PATIENT")
                        .requestMatchers("/annotations/**").hasAnyAuthority("DOCTOR", "RADIOLOGIST")
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/doctor/**").hasAnyAuthority("DOCTOR")
                        .requestMatchers("/radiologist/**").hasAnyAuthority("RADIOLOGIST")
                        .requestMatchers("/patient/**").hasAnyAuthority("PATIENT")
                        .requestMatchers("/lab/**").hasAnyAuthority("LAB")
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthFIlter, UsernamePasswordAuthenticationFilter.class
                );
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(credentialsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
