package com.egen.movieflix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.egen.movieflix.entity.User;
import com.egen.movieflix.repository.UserRepository;
import com.egen.movieflix.security.JwtUser;
import com.egen.movieflix.security.JwtUserFactory;

@Service
public class AuthenticationService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		User user = userRepository.getNewUser(arg0);

		JwtUser jwt = JwtUserFactory.create(user);
		UserDetails userDetails = (UserDetails) jwt;
		System.out.println(userDetails.toString());
		return userDetails;
	}

}
