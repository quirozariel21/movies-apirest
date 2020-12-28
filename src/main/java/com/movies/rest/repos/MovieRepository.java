package com.movies.rest.repos;

import com.movies.rest.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByTitleContainsIgnoreCase(String title, Pageable pageable);
    Page<Movie> findByTitleContainsIgnoreCaseAndAvailability(String title, boolean availability, Pageable pageable);
}
