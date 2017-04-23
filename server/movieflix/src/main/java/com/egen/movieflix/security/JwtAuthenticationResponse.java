package com.egen.movieflix.security;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

	
	private static final long serialVersionUID = -5234425787867665983L;
	private final String token;
	private final Object[] role;
	private final String username;

	public String getUsername() {
		return username;
	}

	public Object[] getRole() {
		return role;
	}

	public JwtAuthenticationResponse(String token, Object[] role,String username) {
		this.token = token;
		this.role = role;
		this.username=username;
	}

	public String getToken() {
		return this.token;
	}
}
