package com.arogyavarta.console.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDTO {
    private String username;
    private String password;
    private String userType;
}
