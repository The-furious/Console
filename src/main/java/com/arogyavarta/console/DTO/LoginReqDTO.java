package com.arogyavarta.console.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDTO {
    private String username;
    private String password;
    private String userType;
}
