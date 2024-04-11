package com.arogyavarta.console.dto;

import lombok.Data;

@Data
public class ActiveUserDTO {
    private Long userId;
    private Status status;
}