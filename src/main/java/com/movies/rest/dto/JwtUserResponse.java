package com.movies.rest.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends GetUserResponse {
	
	private String token;
	
	@Builder(builderMethodName = "jwtUserBuilder")
	public JwtUserResponse(Long id, String username, String fullname, String email, Set<String> roles, String token) {
		super(id, username, fullname, email, roles);
		this.token = token;
	}
}
