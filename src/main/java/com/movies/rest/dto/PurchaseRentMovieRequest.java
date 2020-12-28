package com.movies.rest.dto;


import com.movies.rest.enums.Actions;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRentMovieRequest {

    @NotNull
    private Long movieId;
    @Min(1)
    private long quantity;
    @NotNull
    private Actions actionType;
}
