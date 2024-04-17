package com.arogyavarta.console.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAnnotationDTO {
    private Long imageId;
    private Long radiologistId;
    private String impressionText;
    private MultipartFile imageFile;
    
}
