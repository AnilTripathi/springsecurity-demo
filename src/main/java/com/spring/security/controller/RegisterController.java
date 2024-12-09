package com.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.dto.ApiResponse;
import com.spring.security.dto.SignUpDto;
import com.spring.security.dto.SignUpDto.SignupResponse;
import com.spring.security.service.RegisterService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1")
public class RegisterController {
    private RegisterService registerService;

    @Autowired
    public void setRegisterService(RegisterService registerService){
        this.registerService=registerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> registerUser(@Valid @RequestBody SignUpDto.SignupRequest signUpRequest) {
        ApiResponse<SignupResponse> resp = registerService.registerUser(signUpRequest);
		return ResponseEntity.ok().body(resp);
	}
}
