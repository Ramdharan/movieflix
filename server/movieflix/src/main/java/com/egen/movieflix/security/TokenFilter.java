package com.egen.movieflix.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.egen.movieflix.exception.UserNotAuthenticated;
import com.egen.movieflix.service.AuthenticationService;
import com.egen.movieflix.utils.Constants;

@Component
public class TokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	AuthenticationService authenticationService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// System.out.println(request.getMethod());
		String authToken = request.getHeader(Constants.TOKEN_HEADER);

		if (authToken != null) {
			String username = jwtTokenUtil.getUsernameFromToken(authToken);

			System.out.println("checking info for :" + username);
			if (username != null) {
				UserDetails userDetails = this.authenticationService.loadUserByUsername(username);

				String uri = request.getRequestURI();
				if (uri.equals("/movieflix/catalog/create") || uri.equals("/movieflix/catalog/update")
						|| uri.equals("/movieflix/catalog/delete")) {
					Collection<?> auth = userDetails.getAuthorities();

					Iterator<SimpleGrantedAuthority> it = (Iterator<SimpleGrantedAuthority>) auth.iterator();
					boolean isAdmin = false;
					// Check if the user has ADMIN role before processing the
					// request
					while (it.hasNext()) {
						String role = it.next().getAuthority();
						System.out.println(role);
						if (role.equals("ROLE_ADMIN")) {
							isAdmin = true;
						}
					}
					if (!isAdmin) {

						throw new UserNotAuthenticated(Constants.UNAUTHORIZED_MSG);
					}
				}
				if (jwtTokenUtil.validateToken(authToken, userDetails)) {

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					logger.info("authenticated user " + username + ", setting security context");
					SecurityContextHolder.getContext().setAuthentication(authentication);

				}
			} else {
				throw new UserNotAuthenticated(Constants.UNAUTHORIZED_MSG);
			}
		}
		chain.doFilter(request, response);

	}

}
