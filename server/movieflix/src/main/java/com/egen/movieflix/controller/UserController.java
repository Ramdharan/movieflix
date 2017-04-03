package com.egen.movieflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.egen.movieflix.entity.User;
import com.egen.movieflix.exception.UserAlreadyExistsException;
import com.egen.movieflix.security.JwtAuthenticationResponse;
import com.egen.movieflix.security.JwtUser;
import com.egen.movieflix.service.AuthenticationService;
import com.egen.movieflix.service.UserService;
import com.egen.movieflix.utils.Constants;

/**
 * Handles mapping requests related to the user. Currently handling creating and
 * validating user
 * 
 * @author ramdharandonda
 *
 */
@RestController
@RequestMapping("/")
public class UserController {
	@Autowired
	public AuthenticationService authenticationService;

	@Autowired
	public UserService userService;
	@Autowired
	public AuthenticationManager authenticationManager;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;

	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpStatus signUpUser(@RequestBody User user) {

		if (userService.isUserExists(user.getUsername())) {
			throw new UserAlreadyExistsException(Constants.USER_EXISTS_MSG);
		} else {
			userService.createUser(user);
		}

		return HttpStatus.OK;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public JwtAuthenticationResponse userLogin() {

		JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String token = userService.generateToken(authenticationService.loadUserByUsername(user.getUsername()));
		JwtAuthenticationResponse response = new JwtAuthenticationResponse(token, user.getAuthorities().toString());
		// Currently returning token and roles after valid login.
		// Basing on the roles want to hide controls in UI like delete,edit and
		// update
		// Use this token to make further requests.

		return response;
	}
}
