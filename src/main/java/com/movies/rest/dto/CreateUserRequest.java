package com.movies.rest.dto;

import com.movies.rest.enums.UserRole;
import com.movies.rest.validations.annotation.NullOrNotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
@ApiModel(value = "Create user Request", description = "Payload to create a user")
public class CreateUserRequest {

	@NullOrNotBlank
	@ApiModelProperty(value = "A valid username", allowableValues = "NonEmpty String", allowEmptyValue = false)
	private String username;

	@NullOrNotBlank
	@ApiModelProperty(value = "A valid fullName", allowableValues = "NonEmpty String", allowEmptyValue = false)
	private String fullName;

	@Email
	@ApiModelProperty(value = "A valid email")
	private String email;

	@NotBlank
	@Size(min = 5, max = 15)
	private String password;

	@NotBlank
	@Size(min = 5, max = 15)
	private String password2;

	@NotNull
	private Set<UserRole> roles;

}
