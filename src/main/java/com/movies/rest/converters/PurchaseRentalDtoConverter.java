package com.movies.rest.converters;

import com.movies.rest.dto.ImageResponse;
import com.movies.rest.dto.MovieResponse;
import com.movies.rest.dto.PurchaseRentalResponse;
import com.movies.rest.entities.PurchaseRental;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PurchaseRentalDtoConverter {

    public PurchaseRentalResponse convertPurchaseRentalToPurchaseRentalDto(PurchaseRental purchaseRental){
        List<ImageResponse> images = purchaseRental.getMovie().getImages().stream().map(m -> new ImageResponse(m.getImage())).collect(Collectors.toList());
        return PurchaseRentalResponse.builder()
                .id(purchaseRental.getId())
                .quantity(purchaseRental.getQuantity())
                .typeAction(purchaseRental.getTypeAction())
                .createdAt(purchaseRental.getCreatedAt())
                .deliveryDate(purchaseRental.getDeliveryDate())
                .userId(purchaseRental.getUser().getId())
                .username(purchaseRental.getUser().getUsername())
                .movie(MovieResponse.builder()
                        .id(purchaseRental.getMovie().getId())
                        .title(purchaseRental.getMovie().getTitle())
                        .description(purchaseRental.getMovie().getDescription())
                        .stock(purchaseRental.getMovie().getStock())
                        .rentalPrice(purchaseRental.getMovie().getRentalPrice())
                        .salePrice(purchaseRental.getMovie().getSalePrice())
                        .availability(purchaseRental.getMovie().isAvailability())
                        .noLikes(purchaseRental.getMovie().getNoLikes())
                        .images(images)
                        .build())
                .build();

    }
}
