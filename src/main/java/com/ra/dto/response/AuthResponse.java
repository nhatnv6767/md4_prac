package com.ra.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private boolean success;
    private String message;
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private EmployeeResponse employee;
}
