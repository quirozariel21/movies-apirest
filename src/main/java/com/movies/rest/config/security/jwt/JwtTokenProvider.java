package com.movies.rest.config.security.jwt;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.movies.rest.entities.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.java.Log;

@Log
@Component
public class JwtTokenProvider {

	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	
	@Value("${app.jwt.secret}")
	private String jwtSecret;
	
	@Value("${app.jwt.token-expiration}")
	private Long jwtDurationTokenInMs;

	public String generateToken(Authentication authentication) {
		
		UserEntity userEntity = (UserEntity) authentication.getPrincipal();

		Instant expiryDate = Instant.now().plusMillis(jwtDurationTokenInMs);
		
		return Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", TOKEN_TYPE)
				.setSubject(Long.toString(userEntity.getId()))
				.setIssuedAt(new Date())
				.setExpiration(Date.from(expiryDate))
				.claim("userId", userEntity.getId())
				.claim("username", userEntity.getUsername())
				.claim("email", userEntity.getEmail())
				.claim("roles", userEntity.getRoles().stream()
								.map(x -> x.getRoles().name())
								.collect(Collectors.joining(", "))
						)
				.compact();
	}
	
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
				.parseClaimsJws(token)
				.getBody();
		
		return Long.parseLong(claims.getSubject());
	}
	
	public boolean validateToken(String authToken) {
		
		try {
			Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			log.info("Error in the signature from token JWT: " + ex.getMessage());
		} catch (MalformedJwtException ex) {
			log.info("Token malformed: " + ex.getMessage());
		} catch (ExpiredJwtException ex) {
			log.info("Token has expired: " + ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			log.info("Token JWT don't supported: " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			log.info("JWT claims empty.");
		}
        return false;
			
	}
}
