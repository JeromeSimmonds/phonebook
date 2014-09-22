package com.jeromesimmonds.phonebook.core.bo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.jeromesimmonds.phonebook.core.CoreException;
import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.be.FindFilterType;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.PhoneNumber;
import com.jeromesimmonds.phonebook.core.dao.PhoneNumberDAO;
import com.jeromesimmonds.phonebook.core.dao.PhoneNumberFindSpecification;

/**
 * @author Jerome Simmonds
 *
 */
public class PhoneNumberBOImpl extends AbstractBO<PhoneNumber, Integer, PhoneNumberDAO> implements PhoneNumberBO {

	@Override
	protected Specification<PhoneNumber> newSpecification(FindParameters findParameters) throws CoreException {
		return new PhoneNumberFindSpecification(findParameters);
	}

	@Override
	public List<PhoneNumber> findForContact(Contact contact) {
		FindParameters params = new FindParameters(1, 5)
			.with(FindFilterType.Contact, contact);
		return getDAO().findAll(newSpecification(params));
	}

	@Override
	public void deleteForContact(Contact contact) {
		getDAO().deleteForContact(contact);
	}
}
