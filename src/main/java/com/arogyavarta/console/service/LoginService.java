package com.arogyavarta.console.service;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.arogyavarta.console.dto.ChangePasswordRequest;
import com.arogyavarta.console.dto.LoginReqDTO;
import com.arogyavarta.console.dto.LoginRes;
import com.arogyavarta.console.dto.OTPRequestDTO;
import com.arogyavarta.console.config.Constants;
import com.arogyavarta.console.entity.Credentials;
import com.arogyavarta.console.entity.OTP;
import com.arogyavarta.console.repo.CredentialsRepository;
import com.arogyavarta.console.repo.OTPRepository;
import com.arogyavarta.console.utils.EmailUtility;
import com.arogyavarta.console.utils.JWTUtils;

@Component
public class LoginService {

    @Autowired
    private CredentialsRepository credentialsRepo;

    @Autowired
    private OTPRepository otpRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginRes logIn(LoginReqDTO signinRequest){
        LoginRes response = new LoginRes();

        try {
            Credentials user = credentialsRepo.findByUsername(signinRequest.getUsername()).orElseThrow();
            System.out.println("USER IS: "+ user);
            if(!user.getUserType().toString().equals(signinRequest.getUserType()))
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

    public OTP generateAndSaveOTP(OTPRequestDTO otpReq) {
        Credentials credentials = credentialsRepo.findByUsername(otpReq.getUsername()).orElseThrow();
        System.out.println(credentials.getUser().getEmail() + " Email");
        long generatedOTP = generateRandomOTP();
        OTP otp = OTP.builder()
                    .otp(generatedOTP)
                    .credentials(credentials)
                    .verified(false)
                    .sentDate(new Date())
                    .build();
        EmailUtility.sendEmail(credentials.getUser().getEmail(),
                              Constants.OTP_SUBJECT, 
                              Constants.OTP_BODY + generatedOTP);
        return otpRepo.save(otp);
    }

    private Long generateRandomOTP() {
        // Generate a 6-digit random number
        Random random = new Random();
        return (long) (random.nextInt(900000) + 100000);
    }

    public boolean verifyOTP(OTPRequestDTO otpReq) {
        OTP otp = otpRepo.findTopByCredentialsUsernameOrderBySentDateDesc(otpReq.getUsername());
        if (otp != null && !otp.getVerified() 
            && otp.getOtp().equals(otpReq.getOtp()) 
            && isExpired(otp.getSentDate())) {
            otp.setVerified(true);
            otpRepo.save(otp);
            return true;
        }
        return false;
    }

    public boolean isExpired(Date sentDate) {
        Date currentDate = new Date();
        long difference = currentDate.getTime() - sentDate.getTime(); 
        return difference < Constants.OTP_EXP_TIME;
    }

    public boolean changePassword(ChangePasswordRequest changeReq) {
        OTP otp = otpRepo.findTopByCredentialsUsernameOrderBySentDateDesc(changeReq.getUsername());
        if (otp != null && otp.getVerified() && isExpired(otp.getSentDate())) {
            Credentials credentials = credentialsRepo.findByUsername(changeReq.getUsername()).orElseThrow();
            credentials.setPassword(passwordEncoder.encode(changeReq.getPassword()));
            credentialsRepo.save(credentials);
            otpRepo.delete(otp);
            return true;
        }
        return false;
    }


}
