package com.movies.rest.converters;

import java.util.stream.Collectors;

import com.movies.rest.dto.JwtUserResponse;
import org.springframework.stereotype.Component;

import com.movies.rest.dto.GetUserResponse;
import com.movies.rest.entities.UserEntity;

@Component
public class UserDtoConverter {

	public GetUserResponse convertUserEntityToGetUserDto(UserEntity user) {
		return GetUserResponse.builder()
				.id(user.getId())
				.username(user.getUsername())
				.fullName(user.getFullName())
				.email(user.getEmail())
				.roles(user.getRoles().stream()
									  .map(x -> x.getRoles().name())
									  .collect(Collectors.toSet())
				).build();
	}

	public JwtUserResponse convertUserEntityAndTokenJwtUserResponse(UserEntity userEntity, String jwtToken) {

		return JwtUserResponse
				.jwtUserBuilder()
				.id(userEntity.getId())
				.fullname(userEntity.getFullName())
				.email(userEntity.getEmail())
				.username(userEntity.getUsername())
				.roles(userEntity.getRoles().stream().map(x -> x.getRoles().name()).collect(Collectors.toSet()))
				.token(jwtToken)
				.build();
	}
}
