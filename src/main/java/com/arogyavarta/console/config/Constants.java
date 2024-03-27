package com.arogyavarta.console.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Constants {
    public static final long JWT_EXPIRATION_TIME = 86400000; 
    public static final String JWT_SECRET = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
    public static final String JWT_ENCRYPTION_ALGO = "HmacSHA256"; 
}
