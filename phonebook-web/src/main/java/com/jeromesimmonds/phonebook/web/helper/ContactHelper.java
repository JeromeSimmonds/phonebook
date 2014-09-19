package com.jeromesimmonds.phonebook.web.helper;

import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.be.Findings;
import com.jeromesimmonds.phonebook.core.be.User;

/**
 * @author Jerome Simmonds
 *
 */
public interface ContactHelper {

	public Findings<Contact> getContactsForPage(int page, int nbPerPage, User user, String sort);

	public Findings<Contact> getContactsFromTo(int from, int to, User user, String sort);
}
