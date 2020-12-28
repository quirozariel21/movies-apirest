package com.movies.rest.controllers;

import javax.validation.Valid;

import com.movies.rest.dto.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.movies.rest.converters.UserDtoConverter;
import com.movies.rest.dto.JwtUserResponse;
import com.movies.rest.entities.UserEntity;
import com.movies.rest.config.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@Api(value = "Authorization Rest API", description = "Defines endpoints that can be hit only when the user is not logged in. It's not secured by default.")

@RequiredArgsConstructor
public class AuthenticationController {

	private static final Logger logger = Logger.getLogger(AuthenticationController.class);

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDtoConverter converter;

	/**
	 * Entry point for the user log in. Return the jwt auth token
	 */
	@ApiOperation(value = "Logs the user in to the system and return the auth tokens")
	@PostMapping("/login")
	public ResponseEntity<JwtUserResponse> login(@ApiParam(value = "The LoginRequest payload") @Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserEntity userEntity = (UserEntity) authentication.getPrincipal();
		logger.info("Logged in User returned [API]: " + userEntity.getUsername());
		String jwtToken = jwtTokenProvider.generateToken(authentication);
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(converter.convertUserEntityAndTokenJwtUserResponse(userEntity, jwtToken));
	}
}
