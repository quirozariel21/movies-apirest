package com.movies.rest.services;

import com.movies.rest.Upload.StorageService;
import com.movies.rest.config.security.IAuthenticationFacade;
import com.movies.rest.controllers.FilesController;
import com.movies.rest.dto.CreateMovieRequest;
import com.movies.rest.entities.Image;
import com.movies.rest.entities.Movie;
import com.movies.rest.entities.UserEntity;
import com.movies.rest.enums.UserRole;
import com.movies.rest.repos.MovieRepository;
import com.movies.rest.repos.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService extends BaseService<Movie, Long, MovieRepository> {

    private static final Logger logger = Logger.getLogger(MovieService.class);

    private final StorageService storageService;
    private final IAuthenticationFacade authenticationFacade;
    private final UserEntityRepository userEntityRepository;

    public Movie newMovie(CreateMovieRequest createMovieRequest, List<MultipartFile> files) {

        Set<Image> images = new HashSet<>();
        if(files.isEmpty()){
            throw new IllegalArgumentException("The movie must have at least one image.");
        }

        files.forEach( file -> {
            String urlImage = null;
            if(!file.isEmpty()){
                String image = storageService.store(file);
                urlImage = MvcUriComponentsBuilder.fromMethodName(FilesController.class, "serveFile", image, null)
                                                  .build().toUriString();
                images.add(new Image(urlImage));

            }
        });

        Movie movie = Movie.builder()
                           .title(createMovieRequest.getTitle())
                           .description(createMovieRequest.getDescription())
                           .stock(createMovieRequest.getStock())
                           .rentalPrice(createMovieRequest.getRentalPrice())
                           .salePrice(createMovieRequest.getSalePrice())
                           .availability(createMovieRequest.isAvailability())
                           .noLikes(0L)
                           .images(images)
                           .build();

        return this.save(movie);
    }

    public Page<Movie> findByTitleAndAvailability(Integer pageNo, Integer pageSize,
                                                  String title, Boolean availability){

        Optional<UserEntity> user = userEntityRepository.findByUsername(authenticationFacade.getUserName());
        List<String> userRoles = user.get().getRoles().stream().map(u -> u.getRoles().name()).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("title")
                .ascending()
                .and(Sort.by("noLikes").descending()));

        if(userRoles.size() == 1 && userRoles.contains(UserRole.USER.name())){
            logger.info(String.format("The user: '%s' has USER role.", authenticationFacade.getUserName()));
            return this.repositorio.findByTitleContainsIgnoreCaseAndAvailability(title, true, pageable);
        }

        if(availability == null){
            logger.info(String.format("The user: '%s' has ADMIN role but availability is null.", authenticationFacade.getUserName()));
            return this.repositorio.findByTitleContainsIgnoreCase(title, pageable);
        }

        return this.repositorio.findByTitleContainsIgnoreCaseAndAvailability(title, availability, pageable);
    }
}
