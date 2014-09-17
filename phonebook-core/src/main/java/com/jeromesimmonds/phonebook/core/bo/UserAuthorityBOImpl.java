package com.jeromesimmonds.phonebook.core.bo;

import org.springframework.data.jpa.domain.Specification;

import com.jeromesimmonds.phonebook.core.CoreException;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.UserAuthority;
import com.jeromesimmonds.phonebook.core.dao.UserAuthorityDAO;
import com.jeromesimmonds.phonebook.core.dao.UserAuthorityFindSpecification;

/**
 * @author Jerome Simmonds
 *
 */
public class UserAuthorityBOImpl extends AbstractBO<UserAuthority, Integer, UserAuthorityDAO> implements UserAuthorityBO {

	@Override
	protected Specification<UserAuthority> newSpecification(FindParameters parameters) throws CoreException {
		return new UserAuthorityFindSpecification(parameters);
	}
}
