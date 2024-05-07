package com.arogyavarta.console.dto;

import lombok.Data;

@Data
public class EndConsultationDTO {
    private Long consultationId;
    private String impression;
    private String prescription;
}
