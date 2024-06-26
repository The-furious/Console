package com.arogyavarta.console.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arogyavarta.console.entity.Images;

public interface ImagesRepository extends JpaRepository<Images, Long>{

    List<Images> findByTestsTestId(Long testId);
    
}
