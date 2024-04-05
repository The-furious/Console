package com.arogyavarta.console.DTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class LabUploadDTO {
    private Long consultationId;
    private Long labId;
    private String remarks;
    private String testName;
    private List<MultipartFile> files;   
}
