package com.arogyavarta.console.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.dto.AnnotationDTO;
import com.arogyavarta.console.dto.CreateAnnotationDTO;
import com.arogyavarta.console.entity.Annotations;
import com.arogyavarta.console.entity.Images;
import com.arogyavarta.console.entity.Radiologist;
import com.arogyavarta.console.repo.AnnotationsRepository;
import com.arogyavarta.console.repo.ImagesRepository;
import com.arogyavarta.console.repo.RadiologistRepository;
import com.arogyavarta.console.utils.StorageUtil;

@Service
public class AnnotationsService {

    @Autowired 
    private AnnotationsRepository annotationsRepository;

    @Autowired 
    private RadiologistRepository radiologistRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private StorageUtil storageUtil;

    public String add(CreateAnnotationDTO createAnnotationDTO) {
        Radiologist radiologist = radiologistRepository.findById(createAnnotationDTO.getRadiologistId()).orElseThrow();
        Images image = imagesRepository.findById(createAnnotationDTO.getImageId()).orElseThrow();
        String imageUrl = storageUtil.uploadFile(createAnnotationDTO.getImageFile());
        Annotations annotation = Annotations.builder()
                                            .radiologist(radiologist)
                                            .image(image)
                                            .impressionText(createAnnotationDTO.getImpressionText())
                                            .annotationDate(LocalDateTime.now())
                                            .editedImageUrl(imageUrl)
                                            .build();
        annotationsRepository.save(annotation);
        return "Created annotation";
    }

    public List<AnnotationDTO> getAnnotations(Long radiologistId, Long imageId) {
        List<Annotations> annotations = annotationsRepository.findByImage_ImageIdAndRadiologist_UserIdOrderByAnnotationDateDesc(imageId, radiologistId);
        return annotations.stream().map(this::mapToAnnotationsDTO).collect(Collectors.toList());
    }

    private AnnotationDTO mapToAnnotationsDTO(Annotations annotation)
    {
        return AnnotationDTO.builder()
                            .annotationId(annotation.getAnnotationId())
                            .annotationDate(annotation.getAnnotationDate())
                            .impressionText(annotation.getImpressionText())
                            .imageUrl(storageUtil.generatePresignedUrl(annotation.getEditedImageUrl()))
                            .build();
    }
}
