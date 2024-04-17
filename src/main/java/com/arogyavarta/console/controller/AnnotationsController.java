package com.arogyavarta.console.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arogyavarta.console.dto.AnnotationDTO;
import com.arogyavarta.console.dto.CreateAnnotationDTO;
import com.arogyavarta.console.service.AnnotationsService;

@RestController
@RequestMapping("/annotations")
public class AnnotationsController {

    @Autowired
    private AnnotationsService annotationsService;

    @PostMapping("/add")
    public ResponseEntity<String> upload(CreateAnnotationDTO createAnnotationDTO) {
        String res = annotationsService.add(createAnnotationDTO);
        if (!res.contains("Error")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/{radiologistId}/{imageId}")
    public ResponseEntity<List<AnnotationDTO>> getAnnotations(@PathVariable Long radiologistId,@PathVariable Long imageId){
        List<AnnotationDTO> annotations = annotationsService.getAnnotations(radiologistId, imageId);
        return ResponseEntity.ok(annotations);
    }
}
