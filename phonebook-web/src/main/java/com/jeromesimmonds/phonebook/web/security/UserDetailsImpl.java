package com.jeromesimmonds.phonebook.web.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jeromesimmonds.phonebook.core.be.User;

/**
 * @author Jerome Simmonds
 *
 */
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -6092803942937821759L;
	
	private User user;
	private boolean isLocked = false;
	private Collection<GrantedAuthority> authorities;

	public UserDetailsImpl(User user) {
		super();
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isLocked;
	}
	
	@Override
	public boolean isEnabled() {
		return !user.isDisabled();
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true; // not implemented
	}

	@Override
	public boolean isAccountNonExpired() { // Using it to check email confirmation
		return user.isConfirmed();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
}
