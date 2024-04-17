package com.arogyavarta.console.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Annotations;

public interface AnnotationsRepository extends JpaRepository<Annotations, Long> {
    List<Annotations> findByImage_ImageIdAndRadiologist_UserIdOrderByAnnotationDateDesc(Long imageId, Long radiologistId);
}
