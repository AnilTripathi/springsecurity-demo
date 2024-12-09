package com.spring.security.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.security.dto.ApiResponse;
import com.spring.security.dto.SignUpDto.SignupRequest;
import com.spring.security.dto.SignUpDto.SignupResponse;
import com.spring.security.entity.Role;
import com.spring.security.entity.RoleName;
import com.spring.security.entity.User;
import com.spring.security.exceptions.SignupException;
import com.spring.security.repository.RoleRepository;
import com.spring.security.repository.UserRepository;
import com.spring.security.service.RegisterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
	private PasswordEncoder encoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder encoder){
        this.encoder=encoder;
    }

    @Override
    public ApiResponse<SignupResponse> registerUser(SignupRequest payload) {
        ApiResponse<SignupResponse> apiResponse=new ApiResponse<>();
		if (userRepository.existsByUsername(payload.getUsername())) {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setMessage("Username is already taken!");
            apiResponse.setError("Username is already taken!");
			return apiResponse;
		}

		if (userRepository.existsByEmail(payload.getEmail())) {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setMessage("Email is already in use!");
            apiResponse.setError("Email is already in use!");
			return apiResponse;
		}

		// Creating user's account
		User user = new User(payload.getName(), payload.getUsername(), payload.getEmail(),encoder.encode(payload.getPassword()));

		Set<String> strRoles = payload.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "superdmin":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_SUPER_ADMIN)
						.orElseThrow(() -> new SignupException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);
				
				break;
			case "client":
				Role clientRole = roleRepository.findByName(RoleName.ROLE_CLIENT)
						.orElseThrow(() -> new SignupException("Fail! -> Cause: User Role not find."));
				roles.add(clientRole);

				break;
			case "admin":
				Role pmRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new SignupException("Fail! -> Cause: User Role not find."));
				roles.add(pmRole);

				break;
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
						.orElseThrow(() -> new SignupException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});
		user.setRoles(roles);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		User svdObj = userRepository.save(user);
        SignupResponse signRsp=SignupResponse.builder()
        .email(svdObj.getEmail()).name(svdObj.getName())
        .username(svdObj.getUsername())
        .build();
        apiResponse.setMessage("Success");
        apiResponse.setData(signRsp);
        apiResponse.setStatus(HttpStatus.CREATED);
        return apiResponse;
    }
    
}
