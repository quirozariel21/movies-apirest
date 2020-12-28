package com.movies.rest.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.movies.rest.dto.EditUserRequest;
import com.movies.rest.entities.Roles;
import com.movies.rest.error.exceptions.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.movies.rest.dto.CreateUserRequest;
import com.movies.rest.error.exceptions.NewUserWithDifferentPasswordsException;
import com.movies.rest.entities.UserEntity;
import com.movies.rest.repos.UserEntityRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, Long, UserEntityRepository> {

	private final UserEntityRepository userEntityRepository;
	private final PasswordEncoder passwordEncoder;

	public Optional<UserEntity> findByUsername(String username) {
		return this.repositorio.findByUsername(username);
	}

	public UserEntity newUser(CreateUserRequest request) {

		Set<Roles> roles = request.getRoles().stream().map(r -> new Roles(r)).collect(Collectors.toSet());
		if (request.getPassword().contentEquals(request.getPassword2())) {
			UserEntity userEntity = UserEntity.builder()
					.username(request.getUsername())
					.password(passwordEncoder.encode(request.getPassword()))
					.fullName(request.getFullName()).email(request.getEmail())
					.roles(roles).build();
			try {
				return save(userEntity);
			} catch (DataIntegrityViolationException ex) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("The username: '%s' already exist.", request.getUsername()));
			}
		}

		throw new NewUserWithDifferentPasswordsException();
	}

	public UserEntity editUser(EditUserRequest request, Long userId) {
		Optional<UserEntity> userEntity = userEntityRepository.findById(userId);
		if(!userEntity.isPresent()){
			throw new UserNotFoundException(userId);
		}

		userEntity.get().setUsername(request.getUsername());
		userEntity.get().setFullName(request.getFullName());
		userEntity.get().setEmail(request.getEmail());
		userEntity.get().setRoles(request.getRoles().stream().map(s -> new Roles(s)).collect(Collectors.toSet()));

		return userEntityRepository.save(userEntity.get());
	}
}