package com.spring.security.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface SignUpDto  {

	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public class SignupRequest {
		@NotBlank
		@Size(min = 3, max = 50)
		private String name;

		@NotBlank
		@Size(min = 3, max = 50)
		private String username;

		@NotBlank
		@Size(max = 60)
		@Email
		private String email;

		private Set<String> role;

		@NotBlank
		@Size(min = 6, max = 40)
		private String password;
	}

	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public class SignupResponse {
		private String name;
		private String username;
		private String email;
		private Set<String> role;
	}
}