package com.arogyavarta.console.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.dto.ImageDTO;
import com.arogyavarta.console.entity.Images;
import com.arogyavarta.console.repo.ImagesRepository;
import com.arogyavarta.console.utils.StorageUtil;

@Service
public class ImageService {
    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private StorageUtil storageUtil;

    public List<ImageDTO> getImagesByTestId(Long testId) {
        List<Images> images = imagesRepository.findByTestsTestId(testId);
        List<ImageDTO> imageList = images.stream().map(t-> convertImagesToImageDTO(t)).collect(Collectors.toList());
        return imageList;
    }

    private ImageDTO convertImagesToImageDTO(Images image)
    {
        return ImageDTO.builder()
                        .id(image.getImageId())
                        .imageUrl(storageUtil.generatePresignedUrl(image.getImageUrl()))
                        .imageUrlDCM(storageUtil.generatePresignedUrl(image.getImageUrlJpg()))
                        .build();
    }



}
