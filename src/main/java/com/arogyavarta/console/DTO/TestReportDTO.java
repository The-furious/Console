package com.arogyavarta.console.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.arogyavarta.console.entity.Images;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestReportDTO {
    private String testName;
    private String remarks;
    private LocalDateTime testDate;
    private Long consultationId;
    private List<ImageDTO> images;
}
