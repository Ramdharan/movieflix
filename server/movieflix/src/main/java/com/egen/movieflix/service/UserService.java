package com.egen.movieflix.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.egen.movieflix.entity.User;

public interface UserService {
	public void createUser(User user);

	public boolean isUserExists(String username);

	public String generateToken(UserDetails details);
}
