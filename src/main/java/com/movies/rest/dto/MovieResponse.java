package com.movies.rest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {

    private Long id;
    private String title;
    private String description;
    private Long stock;
    private BigDecimal rentalPrice;
    private BigDecimal salePrice;
    private boolean availability;
    private Long noLikes;
    private List<ImageResponse> images;
}
