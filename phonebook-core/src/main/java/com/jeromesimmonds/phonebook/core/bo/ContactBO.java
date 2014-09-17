package com.jeromesimmonds.phonebook.core.bo;

import java.util.List;

import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.be.User;

/**
 * @author Jerome Simmonds
 *
 */
public interface ContactBO extends BaseBO<Contact, Integer> {
	
	public Contact findById(int contactId);
	
	public List<Contact> findByFirstName(String firstName, User user);
	
	public List<Contact> findByLastName(String lastName, User user);
}
