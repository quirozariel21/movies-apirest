package com.movies.rest.controllers;

import com.movies.rest.dto.EditUserRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.movies.rest.converters.UserDtoConverter;
import com.movies.rest.dto.CreateUserRequest;
import com.movies.rest.dto.GetUserResponse;
import com.movies.rest.services.UserEntityService;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/user")
@Validated
@Api(value = "User Rest API", description = "Defines endpoints for the logged in user. It's secured by default")
@RequiredArgsConstructor
public class UserController {

	private final UserEntityService userEntityService;
	private final UserDtoConverter userDtoConverter;

	/**
	 * Entry point for the user log in. Add a user and return the data saved.
	 */
	@PostMapping("/add")
	public ResponseEntity<GetUserResponse> addUser(@ApiParam(value = "The createUserRequest payload") @Valid @RequestBody CreateUserRequest newUser) {
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(userDtoConverter.convertUserEntityToGetUserDto(userEntityService.newUser(newUser)));
	}

	/**
	 * Entry point for the user log in. Edit a user and return the data saved.
	 */
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> editUser(@RequestBody EditUserRequest request, @PathVariable @Min(1) Long id){
		userEntityService.editUser(request, id);
		return ResponseEntity.status(HttpStatus.OK)
							 .body(userDtoConverter.convertUserEntityToGetUserDto(userEntityService.editUser(request, id)));
	}
}
