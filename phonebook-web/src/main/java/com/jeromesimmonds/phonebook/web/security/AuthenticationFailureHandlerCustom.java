package com.jeromesimmonds.phonebook.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Jerome Simmonds
 *
 */
public class AuthenticationFailureHandlerCustom extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFailureHandlerCustom.class);
	
	@Autowired
	private UsernamePasswordAuthenticationFilter mUsernamePasswordAuthenticationFilter;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
		if (authenticationException != null)
			LOGGER.debug("AuthenticationException: {}", authenticationException.getMessage());

		String usernameParameter = mUsernamePasswordAuthenticationFilter.getUsernameParameter();
		String lastUserName = request.getParameter(usernameParameter);
	        
		LOGGER.debug("Failure for username '{}'", lastUserName);

		//TODO: store failure and do something if too many failures (attack)
		
		super.onAuthenticationFailure(request, response, authenticationException);
	}
}