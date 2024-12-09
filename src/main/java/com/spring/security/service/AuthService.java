package com.spring.security.service;

import com.spring.security.dto.ApiResponse;
import com.spring.security.dto.JwtResponseDto;
import com.spring.security.dto.LoginDto;

public interface AuthService {
    ApiResponse<JwtResponseDto> authenticate(LoginDto payload);
}
