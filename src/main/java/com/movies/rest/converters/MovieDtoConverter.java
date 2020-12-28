package com.movies.rest.converters;

import com.movies.rest.dto.ImageResponse;
import com.movies.rest.dto.MovieResponse;
import com.movies.rest.entities.Movie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieDtoConverter {

    private final ModelMapper modelMapper;

    public MovieResponse convertToDto(Movie movie){
        return modelMapper.map(movie, MovieResponse.class);
    }

    public MovieResponse convertMovieToMovieDto(Movie movie) {
        List<ImageResponse> images = movie.getImages().stream().map(m -> new ImageResponse(m.getImage())).collect(Collectors.toList());
        return MovieResponse.builder()
                       .id(movie.getId())
                       .title(movie.getTitle())
                       .description(movie.getDescription())
                       .stock(movie.getStock())
                       .rentalPrice(movie.getRentalPrice())
                       .salePrice(movie.getSalePrice())
                       .availability(movie.isAvailability())
                       .noLikes(movie.getNoLikes())
                       .images(images)
                       .build();
    }

}
