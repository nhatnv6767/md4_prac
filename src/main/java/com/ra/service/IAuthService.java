package com.ra.service;

import com.ra.dto.request.LoginRequest;
import com.ra.dto.request.RegisterRequest;
import com.ra.dto.response.AuthResponse;

public interface IAuthService {
    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);

    AuthResponse refreshToken(String refreshToken);
}
