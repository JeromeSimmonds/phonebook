package com.jeromesimmonds.phonebook.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jeromesimmonds.phonebook.core.be.User;

/**
 * @author Jerome Simmonds
 *
 */
public interface UserDetailsServiceCustom extends UserDetailsService {
	
	public User getLoggedInUser();

	public void setLoggedInUser(User pUser);
	
	public void logIn(String pEmail, HttpServletRequest request, HttpServletResponse response);
	
	public void clearRememberMe(HttpServletRequest request, HttpServletResponse response);
}
