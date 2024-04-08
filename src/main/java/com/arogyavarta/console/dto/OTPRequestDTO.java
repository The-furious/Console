package com.arogyavarta.console.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPRequestDTO {
    private String username;
    private Long otp;
}
