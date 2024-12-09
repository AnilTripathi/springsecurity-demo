package com.spring.security.service;

import com.spring.security.dto.ApiResponse;
import com.spring.security.dto.SignUpDto;

public interface RegisterService {
    ApiResponse<SignUpDto.SignupResponse> registerUser(SignUpDto.SignupRequest payload);
}
