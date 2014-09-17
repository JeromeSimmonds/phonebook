package com.jeromesimmonds.phonebook.core.bo;

import com.jeromesimmonds.phonebook.core.be.User;

/**
 * @author Jerome Simmonds
 *
 */
public interface UserBO extends BaseBO<User, Integer> {
	
	public User findById(int pUserId);
	
	public User findByUsername(String username);

	public User findByEmail(String email);
	
	public boolean usernameExists(String username);
	
	public boolean emailExists(String email);
}
