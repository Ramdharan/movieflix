package com.egen.movieflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UserNotAuthenticated extends RuntimeException {

	private static final long serialVersionUID = -6589844344777096920L;

	public UserNotAuthenticated(String message) {
		super(message);
	}
}
