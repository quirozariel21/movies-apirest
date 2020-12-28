package com.movies.rest.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.movies.rest.config.security.jwt.JwtAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

	private final UserDetailsService userDetailsService;
	private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAuthorizationFilter jwtAuthorizationFilter;
	private final PasswordEncoder passwordEncoder;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
				"/swagger-ui.html", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint) 
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				// allow anonymous resource requests
				.antMatchers("/",
						"/favicon.ico",
						"/**/*.json",
						"/**/*.xml",
						"/**/*.properties",
						"/**/*.woff2",
						"/**/*.woff",
						"/**/*.ttf",
						"/**/*.ttc",
						"/**/*.ico",
						"/**/*.bmp",
						"/**/*.png",
						"/**/*.gif",
						"/**/*.svg",
						"/**/*.jpg",
						"/**/*.jpeg",
						"/**/*.html",
						"/**/*.css",
						"/**/*.js").permitAll()
				.antMatchers(HttpMethod.POST, "/**/auth/**").permitAll()

				.antMatchers(HttpMethod.POST, "/**/user/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/**/user/**").hasRole("ADMIN")

				.antMatchers(HttpMethod.POST, "/**/movie/add").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/**/movie/rent").hasRole("USER")
				.antMatchers(HttpMethod.POST, "/**/movie/like").hasRole("USER")
				.antMatchers(HttpMethod.PUT, "/**/movie/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/**/movie/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/**/movie/searchByTitle/**").hasAnyRole("ADMIN", "USER")
				.antMatchers(HttpMethod.GET, "/**/movie/searchById/**").permitAll()
				.anyRequest().authenticated();

		http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
