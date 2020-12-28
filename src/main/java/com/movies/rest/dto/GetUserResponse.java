package com.movies.rest.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserResponse {

	private Long id;
	private String username;
	private String fullName;
	private String email;
	private Set<String> roles;

	public GetUserResponse(String username, String fullName, String email, Set<String> roles) {
		this.username = username;
		this.fullName = fullName;
		this.email = email;
		this.roles = roles;
	}
}
