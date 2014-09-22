package com.jeromesimmonds.phonebook.core.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.be.PhoneNumber;

/**
 * @author Jerome Simmonds
 *
 */
public interface PhoneNumberDAO extends BaseDAO<PhoneNumber, Integer> {

	// Could be done without this query, with existing implementation, by loading all entities first, but this is optimized
	@Modifying
	@Query("DELETE FROM PhoneNumber p WHERE p.contact = ?")
	public void deleteForContact(Contact contact);
}
