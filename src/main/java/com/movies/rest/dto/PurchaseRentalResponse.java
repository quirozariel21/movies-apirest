package com.movies.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movies.rest.enums.Actions;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRentalResponse {

    private Long id;
    private Long quantity;
    private Actions typeAction;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate deliveryDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private Long userId;
    private String username;
    private MovieResponse movie;
}
