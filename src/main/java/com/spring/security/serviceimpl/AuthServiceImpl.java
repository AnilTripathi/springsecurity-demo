package com.spring.security.serviceimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.security.config.JwtService;
import com.spring.security.config.UserPrinciple;
import com.spring.security.dto.ApiResponse;
import com.spring.security.dto.JwtResponseDto;
import com.spring.security.dto.LoginDto;
import com.spring.security.repository.RoleRepository;
import com.spring.security.repository.UserRepository;
import com.spring.security.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
	AuthenticationProvider authenticationProvider;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtService jwtService;

    @Override
    public ApiResponse<JwtResponseDto> authenticate(LoginDto payload) {
        ApiResponse<JwtResponseDto> apiResponse=new ApiResponse<>();
        Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserPrinciple userObj = (UserPrinciple) authentication.getPrincipal();
		String roles=userObj.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "));
		Map<String,Object> claim=new HashMap<>();
		claim.put("id",userObj.getId());
		claim.put("username",userObj.getUsername());
		claim.put("role",roles);
		claim.put("email",userObj.getEmail());
		String jwt = jwtService.generateToken(claim,userObj);
        JwtResponseDto resp=new JwtResponseDto(jwt);
        apiResponse.setData(resp);
        apiResponse.setMessage("Success");
        apiResponse.setStatus(HttpStatus.OK);
        return apiResponse;
    }
    
}
