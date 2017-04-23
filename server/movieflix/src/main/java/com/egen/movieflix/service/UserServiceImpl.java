package com.egen.movieflix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egen.movieflix.entity.Authority;
import com.egen.movieflix.entity.User;
import com.egen.movieflix.entity.UserRoles;
import com.egen.movieflix.repository.UserRepository;
import com.egen.movieflix.security.JwtTokenUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	public UserRepository userRepository;
	@Autowired
	public JwtTokenUtil util;

	@Override
	public boolean isUserExists(String username) {
		return userRepository.isUserExists(username);
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		String token = util.generateToken(userDetails);
		System.out.println(util.getUsernameFromToken(token));
		return token;
	}

	@Override
	public void createUser(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setEnabled(true);
		List<Authority> authority = new ArrayList<Authority>();
		Authority auth = new Authority();
		auth.setName(UserRoles.ROLE_USER);

		auth.setUsers(new ArrayList<User>());

		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		auth.setUsers(userlist);
		authority.add(auth);
		user.setAuthorities(authority);
		userRepository.createUser(user);
	}

}
