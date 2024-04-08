package com.arogyavarta.console.dto;

import java.net.URL;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDTO {
    private Long id;
    private URL imageUrl;
}
