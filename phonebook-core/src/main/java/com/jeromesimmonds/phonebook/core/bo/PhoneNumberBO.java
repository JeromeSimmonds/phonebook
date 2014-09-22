package com.jeromesimmonds.phonebook.core.bo;

import java.util.List;

import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.be.PhoneNumber;

/**
 * @author Jerome Simmonds
 *
 */
public interface PhoneNumberBO extends BaseBO<PhoneNumber, Integer> {
	
	public List<PhoneNumber> findForContact(Contact contact);

	public void deleteForContact(Contact contact);
}
