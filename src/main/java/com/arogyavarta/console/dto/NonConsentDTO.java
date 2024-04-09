package com.arogyavarta.console.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonConsentDTO {
    private Long consultationId;
    private Long userId;
    private String name;
}
