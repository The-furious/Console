package com.arogyavarta.console.DTO;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String username;
    private String password;
}
