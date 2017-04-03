package com.egen.movieflix.repository;

import com.egen.movieflix.entity.User;

public interface UserRepository {
	public User createUser(User user);

	public boolean isUserExists(String email);

	public User getNewUser(String username);
}
