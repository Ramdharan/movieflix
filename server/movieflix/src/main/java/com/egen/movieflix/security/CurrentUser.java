package com.egen.movieflix.security;

import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
JwtUser user;

public JwtUser getUser() {
	return user;
}

public void setUser(JwtUser user) {
	this.user = user;
}
}
