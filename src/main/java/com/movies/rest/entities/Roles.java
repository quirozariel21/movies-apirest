package com.movies.rest.entities;

import com.movies.rest.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Roles {

    @Enumerated(EnumType.STRING)
    private UserRole roles;
}
