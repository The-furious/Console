package com.arogyavarta.console.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String username;
    private String password;
}
