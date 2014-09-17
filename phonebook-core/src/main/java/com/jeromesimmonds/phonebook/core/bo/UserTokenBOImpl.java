package com.jeromesimmonds.phonebook.core.bo;

import org.springframework.data.jpa.domain.Specification;

import com.jeromesimmonds.phonebook.core.CoreException;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.UserToken;
import com.jeromesimmonds.phonebook.core.dao.UserTokenDAO;

/**
 * @author Jerome Simmonds
 *
 */
public class UserTokenBOImpl extends AbstractBO<UserToken, String, UserTokenDAO> implements UserTokenBO {

	@Override
	protected Specification<UserToken> newSpecification(FindParameters findParameters) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(int userId, int typeId) {
		getDAO().deleteAll(userId, typeId);
	}
}
