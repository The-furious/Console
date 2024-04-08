package com.arogyavarta.console.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.dto.TestReportDTO;
import com.arogyavarta.console.entity.Tests;
import com.arogyavarta.console.repo.TestsRepository;

@Service
public class TestService {
    @Autowired
    private TestsRepository testsRepository;

    @Autowired
    private ImageService imageService;

    public List<Tests> findAllTestsForLabId(Long labId) {
        return testsRepository.findByLabUserId(labId);
    }

    public TestReportDTO getTestReports(Long consultationId) {
        Tests tests =  testsRepository.findTopByConsultationConsultationId(consultationId);
        
        TestReportDTO reports = convertToTestReportDTO(tests);

        return reports;
    }
    private TestReportDTO convertToTestReportDTO(Tests test)
    {
        return TestReportDTO.builder()
                            .consultationId(test.getConsultation().getConsultationId())
                            .remarks(test.getRemarks())
                            .testName(test.getTestName())
                            .testDate(test.getTestDate())
                            .images(imageService.getImagesByTestId(test.getTestId()))
                            .build();
    }
}
