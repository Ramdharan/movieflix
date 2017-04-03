package com.egen.movieflix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CatalogDoestNotExists extends RuntimeException {

	private static final long serialVersionUID = 2457005461195720100L;

	public CatalogDoestNotExists(String message) {
		super(message);
	}
}
