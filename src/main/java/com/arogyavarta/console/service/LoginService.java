package com.arogyavarta.console.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.arogyavarta.console.DTO.LoginReqDTO;
import com.arogyavarta.console.DTO.LoginRes;
import com.arogyavarta.console.entity.Credentials;
import com.arogyavarta.console.repo.CredentialsRepository;
import com.arogyavarta.console.utils.JWTUtils;

@Component
public class LoginService {

    @Autowired
    private CredentialsRepository credentialsRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    public LoginRes logIn(LoginReqDTO signinRequest){
        LoginRes response = new LoginRes();

        try {
            Credentials user = credentialsRepo.findByUsername(signinRequest.getUsername()).orElseThrow();
            System.out.println("USER IS: "+ user);
            if( user.getUserType().toString().equals(signinRequest.getUserType())==false)
            {
                throw new Exception("Role mismatch");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(),signinRequest.getPassword()));
            var jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setUserId(user.getUser().getUserId());
            response.setMessage("Successfully Signed In");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }
}
