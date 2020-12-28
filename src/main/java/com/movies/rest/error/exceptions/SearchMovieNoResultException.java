package com.movies.rest.error.exceptions;

public class SearchMovieNoResultException extends RuntimeException{

    private static final long serialVersionUID = -889312292404205516L;

    public SearchMovieNoResultException() {
        super("The search cannot found results.");
    }

    public SearchMovieNoResultException(String text) {
        super(String.format("Movie with name '%' not found.", text));
    }
}
