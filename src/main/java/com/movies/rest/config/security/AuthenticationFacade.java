package com.movies.rest.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    private Authentication authentication;

    public void init(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getUserName(){
        init();
        return authentication.getName();
    }
}
