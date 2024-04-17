package com.arogyavarta.console.dto;

import java.net.URL;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnnotationDTO {
    private Long annotationId;
    private URL imageUrl;
    private LocalDateTime annotationDate;
    private String impressionText;
}
