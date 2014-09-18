package com.jeromesimmonds.phonebook.core.bo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.jeromesimmonds.phonebook.core.CoreException;
import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.be.FindFilterType;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.User;
import com.jeromesimmonds.phonebook.core.dao.ContactDAO;
import com.jeromesimmonds.phonebook.core.dao.ContactFindSpecification;

/**
 * @author Jerome Simmonds
 *
 */
public class ContactBOImpl extends AbstractBO<Contact, Integer, ContactDAO> implements ContactBO {

	@Override
	protected Specification<Contact> newSpecification(FindParameters findParameters) throws CoreException {
		return new ContactFindSpecification(findParameters);
	}

	@Override
	public Contact findById(int userId) {
		FindParameters params = new FindParameters()
			.with(FindFilterType.Contact, userId);
		return getDAO().findOne(newSpecification(params));
	}

	@Override
	public List<Contact> findByFirstName(String firstName, User user) {
		FindParameters params = new FindParameters(1, 100)
			.with(FindFilterType.FirstName, firstName)
			.with(FindFilterType.User, user);
		return getDAO().findAll(newSpecification(params));
	}

	@Override
	public List<Contact> findByLastName(String lastName, User user) {
		FindParameters params = new FindParameters(1, 100)
			.with(FindFilterType.LastName, lastName)
			.with(FindFilterType.User, user);
		return getDAO().findAll(newSpecification(params));
	}
}
