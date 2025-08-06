package com.testing_company.case_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDTO {
    String accessToken;
    String refreshToken;
    String tokenType;
    Long expiresIn;
}
