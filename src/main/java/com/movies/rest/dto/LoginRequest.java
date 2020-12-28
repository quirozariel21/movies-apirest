package com.movies.rest.dto;

import javax.validation.constraints.NotNull;

import com.movies.rest.validations.annotation.NullOrNotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

@ApiModel(value = "Login Request", description = "The login request payload")
public class LoginRequest {

	@NullOrNotBlank(message = "Login Username can be null but not blank")
	@ApiModelProperty(value = "Registered username", allowableValues = "NonEmpty String", allowEmptyValue = false)
	private String username;

	@NotNull(message = "Login password cannot be blank")
	@ApiModelProperty(value = "Valid user password", required = true, allowableValues = "NonEmpty String")
	private String password;
}
