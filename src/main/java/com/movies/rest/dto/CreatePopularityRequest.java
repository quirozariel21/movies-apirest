package com.movies.rest.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePopularityRequest {

    @NotNull
    private Long movieId;
    private boolean like;
}
