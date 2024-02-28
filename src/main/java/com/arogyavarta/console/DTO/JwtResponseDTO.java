package com.arogyavarta.console.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO {
    private String accessToken;
  
}