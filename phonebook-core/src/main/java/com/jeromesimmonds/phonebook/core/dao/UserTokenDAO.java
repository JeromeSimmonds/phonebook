package com.jeromesimmonds.phonebook.core.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jeromesimmonds.phonebook.core.be.UserToken;

/**
 * @author Jerome Simmonds
 *
 */
public interface UserTokenDAO extends BaseDAO<UserToken, String> {
	
	@Modifying
	@Query("DELETE FROM UserToken ut WHERE ut.user.id = ? AND ut.typeId = ?")
	public void deleteAll(int userId, int typeId);
}
