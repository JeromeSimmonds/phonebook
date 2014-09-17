package com.jeromesimmonds.phonebook.core.dao;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;

import com.jeromesimmonds.phonebook.core.be.User;

/**
 * @author Jerome Simmonds
 *
 */
public interface UserDAO extends BaseDAO<User, Integer> {

	@Query("FROM User WHERE username = ?")
	public User findByUsername(String username);

	@Query("FROM User WHERE email = ?")
	public User findByEmail(String email);
	
	// Native query is optimized
	@Query(nativeQuery = true, value = "SELECT 1 FROM users WHERE username = ? LIMIT 1") // SELECT EXISTS (SELECT 1 FROM users WHERE username = ?)
	public BigInteger usernameExists(String username);
	
	// Native query is optimized
	@Query(nativeQuery = true, value = "SELECT 1 FROM users WHERE email = ? LIMIT 1") // SELECT EXISTS (SELECT 1 FROM users WHERE email = ?)
	public BigInteger emailExists(String email);
}
