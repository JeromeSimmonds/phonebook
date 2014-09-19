package com.jeromesimmonds.phonebook.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;

import com.jeromesimmonds.phonebook.core.CoreException;
import com.jeromesimmonds.phonebook.core.be.FindFilterType;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.User;
import com.jeromesimmonds.phonebook.core.be.UserAuthority;
import com.jeromesimmonds.phonebook.core.bo.UserAuthorityBO;
import com.jeromesimmonds.phonebook.core.bo.UserBO;
import com.jeromesimmonds.phonebook.web.Constants;

/**
 * @author Jerome Simmonds
 *
 */
public class UserDetailsServiceCustomImpl implements UserDetailsServiceCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceCustomImpl.class);
	
	@Autowired
	private UserBO userBO;
	@Autowired
	private UserAuthorityBO userAuthorityBO;
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandlerCustom;
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {
		try {
			User user = userBO.findByEmail(email);
			if (user == null) {
				throw new UsernameNotFoundException("Could not find user '" + email + "'");
			}

			UserDetailsImpl userDetails = new UserDetailsImpl(user);
			userDetails.setAuthorities(getAuthorities(user));
			
			return userDetails;
		} catch (DataAccessException e) {
			LOGGER.error("Error loading user '{}': {}", email, e);
			throw e;
		} catch (Throwable t) {
			LOGGER.error("Unexpected error loading user '{}': {}", email, t);
		}
		return null;
	}
	
	private Collection<GrantedAuthority> getAuthorities(User user) {
		FindParameters params = new FindParameters(0, 10).with(FindFilterType.User, user.getId());
		List<UserAuthority> results = null;
		try {
			results = userAuthorityBO.find(params).getResults();
		} catch (CoreException e) {
			throw new UsernameNotFoundException(user.getEmail());
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (UserAuthority auth : results)
			authorities.add(new SimpleGrantedAuthority(auth.getAuthority().getAuthority()));
	
		return authorities;
	}

	public void logIn(String email, HttpServletRequest request, HttpServletResponse response) {
		UserDetails userDetails = loadUserByUsername(email);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		try {
			authenticationSuccessHandlerCustom.onAuthenticationSuccess(request, response, authentication);
		} catch (Exception e) {
			LOGGER.error("Unexpected error auto login user " + email, e);
		}
	}

	public void clearRememberMe(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, null); //"SPRING_SECURITY_REMEMBER_ME_COOKIE";
		cookie.setMaxAge(0);
        String contextPath = request.getContextPath();
		cookie.setPath(contextPath.length() > 0 ? contextPath : Constants.SLASH);
		response.addCookie(cookie);
	}
    
	public User getLoggedInUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetailsImpl) {
			return ((UserDetailsImpl) principal).getUser();
		} else {
			return null;
		}
	}

	public void setLoggedInUser(User user) {
		((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).setUser(user);
	}
}
