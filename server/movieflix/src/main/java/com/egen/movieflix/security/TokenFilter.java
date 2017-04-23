package com.egen.movieflix.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
import com.fasterxml.jackson.databind.ObjectMapper;
@Order(1)
@Component
public class TokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	AuthenticationService authenticationService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		 System.out.println(request.getMethod());
		 System.out.println("filter called");
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
		}else{
			System.out.println("Got null auth header");
		}
		
		//Add CORS headers
           response.addHeader("Access-Control-Allow-Origin", "*");
		
		if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
			// CORS "pre-flight" request
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5,  Date, X-Api-Version, X-File-Name,Authorization");
			response.addHeader("Access-Control-Max-Age", "3600");
			System.out.println("Added headers for CROS Support");
		
		}
		if (request.getRequestURI().equals("/movieflix/login")&&request.getMethod().equals("POST")) 
		{
			chain.doFilter(new JsonConvertFilter((HttpServletRequest)request), response);
				
		}else{
		chain.doFilter(request, response);
		}
	}

	
	 public class JsonConvertFilter extends HttpServletRequestWrapper {

	     //   private final Logger LOG = LoggerFactory.getLogger(JsonConvertFilter.class);

	        private LoginRequest userDetails;

	        public JsonConvertFilter(HttpServletRequest request) {
	            super((HttpServletRequest)request);
	            userDetails = getJson();
	        }

	        public String getParameter(String key){


	            if(userDetails!=null){
	                if("username".equals(key)){
	                    return  userDetails.getUsername();
	                }
	                if("password".equals(key)){
	                    return userDetails.getPassword();
	                }
	            }
	            System.out.println("Called wrapper");
	            return super.getParameter(key);
	        }

	        private LoginRequest getJson() {

	            try {
	    	        ObjectMapper objectMapper = new ObjectMapper();

	                 List<String> data =  IOUtils.readLines(super.getReader());
	                final String jsonData = data.stream().collect(Collectors.joining());
		               System.out.println("receivd data"+jsonData);

	                
	                // LOG.info(jsonData);
		               LoginRequest userDetails = objectMapper.readValue(jsonData, LoginRequest.class);
	               System.out.println("receivd data"+jsonData);
	                return userDetails;
	            } catch (IOException e) {
	            	e.printStackTrace();
	               // LOG.warn("Failed to read data {}", e.getMessage(), e);
	                return null;
	            }

	        }

	    }
	
}
