package com.movies.rest.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movies.rest.entities.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity> findByUsername(String username);
}
