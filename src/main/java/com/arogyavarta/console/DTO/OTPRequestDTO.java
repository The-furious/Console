package com.arogyavarta.console.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPRequestDTO {
    private String username;
    private Long otp;
}
