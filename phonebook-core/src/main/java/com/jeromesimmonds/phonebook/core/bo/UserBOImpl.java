package com.jeromesimmonds.phonebook.core.bo;

import org.springframework.data.jpa.domain.Specification;

import com.jeromesimmonds.phonebook.core.CoreException;
import com.jeromesimmonds.phonebook.core.be.FindFilterType;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.User;
import com.jeromesimmonds.phonebook.core.dao.UserDAO;
import com.jeromesimmonds.phonebook.core.dao.UserFindSpecification;

/**
 * @author Jerome Simmonds
 *
 */
public class UserBOImpl extends AbstractBO<User, Integer, UserDAO> implements UserBO {

	@Override
	protected Specification<User> newSpecification(FindParameters findParameters) throws CoreException {
		return new UserFindSpecification(findParameters);
	}

	@Override
	public User findById(int userId) {
		FindParameters params = new FindParameters()
			.with(FindFilterType.User, userId);
		return getDAO().findOne(newSpecification(params));
	}
	
	@Override
	public User findByUsername(String username) {
		return getDAO().findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return getDAO().findByEmail(email);
	}
	
	@Override
	public boolean usernameExists(String username) {
		return getDAO().usernameExists(username) != null;
	}
	
	@Override
	public boolean emailExists(String email) {
		return getDAO().emailExists(email) != null;
	}
}
