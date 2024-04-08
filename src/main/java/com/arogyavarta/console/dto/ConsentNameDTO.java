package com.arogyavarta.console.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsentNameDTO {
    private Boolean givenConsent;
    private String name;
    private Long userId;
    private String userType;
}
