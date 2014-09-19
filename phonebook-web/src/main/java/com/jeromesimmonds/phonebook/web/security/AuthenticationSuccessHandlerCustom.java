package com.jeromesimmonds.phonebook.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author Jerome Simmonds
 *
 */
public class AuthenticationSuccessHandlerCustom extends SavedRequestAwareAuthenticationSuccessHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationSuccessHandlerCustom.class);
	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		LOGGER.debug("Authentication success: {}", user.getUsername());
		
		//TODO: clean failed logins if we store them
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		// if my own strategy about redirecting
		return super.determineTargetUrl(request, response);
	}
}