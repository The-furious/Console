package com.arogyavarta.console.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LabHistoryDTO {
    private String testName;
    private String remarks;
    private LocalDateTime testDate;
    private Long consultationId;
    private String patientName;
}
