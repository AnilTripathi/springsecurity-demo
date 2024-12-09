package com.spring.security.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.dto.ApiResponse;
import com.spring.security.dto.JwtResponseDto;
import com.spring.security.dto.LoginDto;
import com.spring.security.service.AuthService;

@RestController
@RequestMapping("/v1")
public class AuthenticationController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<ApiResponse<JwtResponseDto>> authenticateUser(@RequestBody @Valid LoginDto loginRequest) {
		ApiResponse<JwtResponseDto> authResp=authService.authenticate(loginRequest);
		return ResponseEntity.ok(authResp);
	}

}