package com.arogyavarta.console.dto;

import lombok.Data;

@Data
public class GiveConsentDTO {
    private Long userId;
    private Long consultationId;
    private Boolean consentGiven;
}
