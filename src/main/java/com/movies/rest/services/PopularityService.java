package com.movies.rest.services;

import com.movies.rest.config.security.IAuthenticationFacade;
import com.movies.rest.dto.CreatePopularityRequest;
import com.movies.rest.entities.Movie;
import com.movies.rest.entities.Popularity;
import com.movies.rest.entities.UserEntity;
import com.movies.rest.repos.MovieRepository;
import com.movies.rest.repos.PopularityRepository;
import com.movies.rest.repos.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PopularityService {

    private static final Logger logger = Logger.getLogger(PopularityService.class);

    private final IAuthenticationFacade authenticationFacade;
    private final PopularityRepository popularityRepository;
    private final MovieRepository movieRepository;
    private final UserEntityRepository userEntityRepository;

    public Popularity newPopularity(CreatePopularityRequest request){

        Optional<UserEntity> user = userEntityRepository.findByUsername(authenticationFacade.getUserName());
        Optional<Movie> movie = movieRepository.findById(request.getMovieId());

        Optional<Popularity> popularityFound = popularityRepository.findByUserIdAndMovieIdJoinFetch(user.get().getId(), movie.get().getId());


        if(popularityFound.isPresent()){
            logger.info(String.format("The userId: '%s' already like the movieId: '%s'", user.get().getId(), movie.get().getId()));
             Popularity popularityEdited = popularityFound.get();
             popularityEdited.setLike(request.isLike());
            return popularityRepository.save(popularityEdited);
        }

        logger.info("Creating new popularity object.");
        Popularity popularity = Popularity.builder()
                .like(request.isLike())
                .movie(movie.get())
                .user(user.get())
                .build();
        return popularityRepository.save(popularity);



    }
}
