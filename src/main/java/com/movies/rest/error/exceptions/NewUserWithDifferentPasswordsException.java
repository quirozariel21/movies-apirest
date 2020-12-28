package com.movies.rest.error.exceptions;

public class NewUserWithDifferentPasswordsException extends RuntimeException{

	private static final long serialVersionUID = -6939147523502247594L;
	
	public NewUserWithDifferentPasswordsException() {
		super("The passwords are not the equals.");
	}

}
