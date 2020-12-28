package com.movies.rest.repos;

import com.movies.rest.entities.Popularity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PopularityRepository extends JpaRepository<Popularity, Long> {

    @Query("select  p from Popularity p left join fetch p.user u left join fetch p.movie m where u.id = :userId and m.id = :movieId")
    Optional<Popularity> findByUserIdAndMovieIdJoinFetch(@Param("userId") Long userId, @Param("movieId") Long movieId);

}
