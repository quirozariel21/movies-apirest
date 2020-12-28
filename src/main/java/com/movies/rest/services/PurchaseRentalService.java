package com.movies.rest.services;

import com.movies.rest.config.security.IAuthenticationFacade;
import com.movies.rest.dto.PurchaseRentMovieRequest;
import com.movies.rest.entities.Movie;
import com.movies.rest.entities.PurchaseRental;
import com.movies.rest.entities.UserEntity;
import com.movies.rest.enums.Actions;
import com.movies.rest.error.exceptions.MovieNotFoundException;
import com.movies.rest.repos.MovieRepository;
import com.movies.rest.repos.PurchaseRentalRepository;
import com.movies.rest.repos.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseRentalService {

    private static final Logger logger = Logger.getLogger(PurchaseRentalService.class);

    private final IAuthenticationFacade authenticationFacade;
    private final PurchaseRentalRepository purchaseRentalRepository;
    private final MovieRepository movieRepository;
    private final UserEntityRepository userEntityRepository;

    @Value("${days.delivery-movie}")
    private long daysToDeliveryMovie;


    public PurchaseRental rentMovie(PurchaseRentMovieRequest request){

        Optional<UserEntity> user = userEntityRepository.findByUsername(authenticationFacade.getUserName());

        Optional<Movie> movie = movieRepository.findById(request.getMovieId());

        if(!movie.isPresent()){
            throw new MovieNotFoundException();
        }

        if(!movie.get().isAvailability()){
            throw  new MovieNotFoundException(String.format("Movie with Id: '%s' is not available to %s.", request.getMovieId(), request.getActionType()));
        }

        if(movie.get().getStock() == 0){
            throw  new MovieNotFoundException(String.format("There are no movies in the stock to %s.", request.getActionType()));
        }

        if(request.getQuantity() > movie.get().getStock() ){
            throw  new MovieNotFoundException(String.format("There are no '%s' movies in the stock to %s.", request.getQuantity(), request.getActionType()));
        }

        logger.info("Reducing movies stock");
        Long stock = movie.get().getStock() - request.getQuantity();
        boolean availability = stock == 0 ? false : true;
        movie.get().setStock(stock);
        movie.get().setAvailability(availability);
        movieRepository.save(movie.get());

        LocalDate deliveryDate = null;
        if(Actions.RENTAL == request.getActionType()){
            deliveryDate = LocalDate.now().plusDays(daysToDeliveryMovie);
        }

        PurchaseRental purchaseRental = PurchaseRental.builder()
                .movie(movie.get())
                .user(user.get())
                .quantity(request.getQuantity())
                .deliveryDate(deliveryDate)
                .typeAction(request.getActionType())
                .penalty(false)
                .build();

         return purchaseRentalRepository.save(purchaseRental);
    }
}

