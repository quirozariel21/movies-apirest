package com.movies.rest.controllers;

import com.movies.rest.converters.MovieDtoConverter;
import com.movies.rest.converters.PurchaseRentalDtoConverter;
import com.movies.rest.dto.*;
import com.movies.rest.entities.Movie;
import com.movies.rest.entities.Popularity;
import com.movies.rest.error.exceptions.MovieNotFoundException;
import com.movies.rest.error.exceptions.SearchMovieNoResultException;
import com.movies.rest.services.MovieService;
import com.movies.rest.services.PopularityService;
import com.movies.rest.services.PurchaseRentalService;
import com.movies.rest.utils.PaginationLinksUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
@Validated
@Api(value = "Movie Rest API", description = "Defines endpoints for the logged in user. It's secured by default")

@RequiredArgsConstructor
public class MovieController {

    private static final Logger logger = Logger.getLogger(MovieController.class);

    private final MovieService movieService;
    private final PopularityService popularityService;
    private final PurchaseRentalService purchaseRentalService;
    private final MovieDtoConverter movieDtoConverter;
    private final PurchaseRentalDtoConverter purchaseRentalDtoConverter;
    private final PaginationLinksUtils paginationLinksUtils;

    /**
     * Entry point for the user log in. Add a movie and return the data saved.
     */
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Returns the current movie created.")
    public ResponseEntity<MovieResponse> addMovie(@ApiParam(value = "The createMovieRequest payload")
                                                  @Valid @RequestPart("newMovie") CreateMovieRequest createMovieRequest,
                                                  @RequestPart(name = "files") List<MultipartFile> files){
        return ResponseEntity.status(HttpStatus.CREATED)
                              .body(movieDtoConverter.convertMovieToMovieDto(movieService.newMovie(createMovieRequest, files)));
    }

    /**
     * Entry point for the user log in. Edit a movie and return the data edited.
     */
    @PutMapping("/edit/{id}")
    @ApiOperation(value = "Returns the current movie edited.")
    public ResponseEntity<MovieResponse> editMovie(@ApiParam(value = "The editMovieRequest payload")
                                                   @Valid @RequestBody EditMovieRequest editMovieRequest,
                                                   @PathVariable @Min(1) Long id){

        return movieService.findById(id).map(m -> {
            m.setTitle(editMovieRequest.getTitle());
            m.setDescription(editMovieRequest.getDescription());
            m.setStock(editMovieRequest.getStock());
            m.setRentalPrice(editMovieRequest.getRentalPrice());
            m.setSalePrice(editMovieRequest.getSalePrice());
            m.setAvailability(editMovieRequest.isAvailability());
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(movieDtoConverter.convertToDto(movieService.save(m)));
        }).orElseThrow(() -> new MovieNotFoundException());
    }

    /**
     * Entry point for the user log in. Delete a movie.
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a movie.")
    public ResponseEntity<Void> deleteMovie(@ApiParam(value = "The movie id") @PathVariable @Min(1) Long id){
        Movie movie = movieService.findById(id).orElseThrow(() -> new MovieNotFoundException(id));

        movieService.delete(movie);

        logger.info("Movie deleted with ID: " + id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Entry point for the user log in. Search a movie by ID.
     */
    @GetMapping("/searchById/{id}")
    @ApiOperation(value = "Returns a movie search by ID.")
    public ResponseEntity<MovieResponse> getMovieById(@ApiParam(value = "The movie id") @PathVariable @Min(1) Long id){
        return movieService.findById(id).map(m -> ResponseEntity.status(HttpStatus.OK)
                                                                .body(movieDtoConverter.convertMovieToMovieDto(m)))
                                                                 .orElseThrow(() -> new MovieNotFoundException());
    }

    @GetMapping("/searchByTitle")
    public ResponseEntity<?> searchMovieByTitle(@RequestParam(defaultValue = "1") @Min(0) Integer pageNo,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                @RequestParam(name = "title", required = false, defaultValue = "") String title,
                                                @RequestParam(name = "availability", required = false) Boolean availability,
                                                HttpServletRequest request){

        Page<Movie> movies = movieService.findByTitleAndAvailability(pageNo, pageSize, title, availability);

        if(movies.isEmpty()){
            throw new SearchMovieNoResultException();
        }

        Page<MovieResponse> movieDtoList = movies.map(movieDtoConverter::convertToDto);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(movieDtoList, uriComponentsBuilder))
                .body(movieDtoList);
    }

    @PostMapping("/like")
    public ResponseEntity<Popularity> like(@RequestBody CreatePopularityRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(popularityService.newPopularity(request));
    }

    @PostMapping("/purchase-rent")
    public ResponseEntity<PurchaseRentalResponse> rentMovie(@RequestBody PurchaseRentMovieRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseRentalDtoConverter.convertPurchaseRentalToPurchaseRentalDto(purchaseRentalService.rentMovie(request)));
    }
}
