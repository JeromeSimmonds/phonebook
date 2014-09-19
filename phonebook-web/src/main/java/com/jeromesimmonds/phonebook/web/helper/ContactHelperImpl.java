package com.jeromesimmonds.phonebook.web.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.be.FindFilterType;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.FindSort;
import com.jeromesimmonds.phonebook.core.be.Findings;
import com.jeromesimmonds.phonebook.core.be.User;
import com.jeromesimmonds.phonebook.core.bo.ContactBO;

/**
 * @author Jerome Simmonds
 *
 */
public class ContactHelperImpl implements ContactHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactHelper.class);
	
	@Autowired
	private ContactBO contactBO;

	@Override
	public Findings<Contact> getContactsForPage(int page, int nbPerPage, User user, String sort) {
		return getContactsFromTo(page * nbPerPage - nbPerPage + 1, page * nbPerPage, user, sort);
	}

	@Override
	public Findings<Contact> getContactsFromTo(int from, int to, User user, String sort) {
		FindParameters params = new FindParameters(from, to)
			.with(FindFilterType.User, user);
		addSort(params, sort);

		return contactBO.find(params);
	}
	
	private void addSort(FindParameters params, String sort) {
		if (sort == null)
			params.with(FindSort.Alpha);
		else {
			try {
				params.with(FindSort.valueOf(sort));
			} catch (Exception e) {
				params.with(FindSort.Alpha);
			}
		}
	}
}
