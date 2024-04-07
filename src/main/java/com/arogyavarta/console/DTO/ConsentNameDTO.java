package com.arogyavarta.console.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsentNameDTO {
    private Boolean givenConsent;
    private Long userId;
}
