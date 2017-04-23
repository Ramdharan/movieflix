package com.egen.movieflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)

public class InvalidUserException extends RuntimeException {

	private static final long serialVersionUID = 366232990626108110L;

	public InvalidUserException(String message) {
		super(message);
	}

}
