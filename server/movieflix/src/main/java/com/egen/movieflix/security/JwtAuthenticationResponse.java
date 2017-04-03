package com.egen.movieflix.security;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

	
	private static final long serialVersionUID = -5234425787867665983L;
	private final String token;
	private final String role;

	public String getRole() {
		return role;
	}

	public JwtAuthenticationResponse(String token, String role) {
		this.token = token;
		this.role = role;
	}

	public String getToken() {
		return this.token;
	}
}
