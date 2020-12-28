package com.movies.rest.dto;

import com.movies.rest.enums.UserRole;
import com.movies.rest.validations.annotation.NullOrNotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "Edit user Request", description = "Payload to edit a user")
public class EditUserRequest {

    @NullOrNotBlank
    @ApiModelProperty(value = "A valid username", allowableValues = "NonEmpty String", allowEmptyValue = false)
    private String username;

    @NullOrNotBlank
    @ApiModelProperty(value = "A valid fullName", allowableValues = "NonEmpty String", allowEmptyValue = false)
    private String fullName;

    @Email
    @ApiModelProperty(value = "A valid email")
    private String email;

    @NotNull
    private Set<UserRole> roles;
}
