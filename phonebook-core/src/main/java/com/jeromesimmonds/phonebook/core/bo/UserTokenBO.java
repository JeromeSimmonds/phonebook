package com.jeromesimmonds.phonebook.core.bo;

import com.jeromesimmonds.phonebook.core.be.UserToken;

/**
 * @author Jerome Simmonds
 *
 */
public interface UserTokenBO extends BaseBO<UserToken, String> {
	
	public void deleteAll(int userId, int typeId);
}
