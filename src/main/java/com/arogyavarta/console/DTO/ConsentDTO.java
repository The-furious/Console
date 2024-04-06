package com.arogyavarta.console.DTO;

import com.arogyavarta.console.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsentDTO {
    private LocalDateTime consentDate;
    private Boolean givenConsent;
    private UserType userType;
    private Long consultationId;
    private Long userId;
}
