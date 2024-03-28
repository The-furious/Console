package com.arogyavarta.console.DTO;


import com.arogyavarta.console.entity.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRes {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String expirationTime;
    private long userId;
    private UserType userType;
}
