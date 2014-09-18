package com.jeromesimmonds.phonebook.core.dao;

import java.util.Calendar;
import java.util.Date;

import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.be.PhoneNumber;
import com.jeromesimmonds.phonebook.core.be.User;
import com.jeromesimmonds.phonebook.core.be.UserToken;

/**
 * @author Jerome Simmonds
 *
 */
public class TestData {

	public final static User USER_1 = new User(1);
	public final static User USER_2 = new User(2);
	
	public final static Contact CONTACT_1 = new Contact(1);
	public final static Contact CONTACT_2 = new Contact(2);
	public final static Contact CONTACT_3 = new Contact(3);
	
	public final static UserToken USERTOKEN_1 = new UserToken();
	
	public final static PhoneNumber PHONE_NUMBER_1 = new PhoneNumber(1);
	public final static PhoneNumber PHONE_NUMBER_2 = new PhoneNumber(2);
	public final static PhoneNumber PHONE_NUMBER_3 = new PhoneNumber(3);
	public final static PhoneNumber PHONE_NUMBER_4 = new PhoneNumber(4);

	static {
		USER_1.setUsername("Username1");
		USER_1.setEmail("user1@site.com");
		USER_2.setUsername("Username2");
		USER_2.setEmail("user2@site.com");
		
		CONTACT_1.setFirstName("John");
		CONTACT_1.setLastName("Smith");
		CONTACT_1.setUser(USER_1);
		CONTACT_2.setFirstName("Annie");
		CONTACT_2.setLastName("Bernard");
		CONTACT_2.setUser(USER_1);
		CONTACT_3.setFirstName("Jean");
		CONTACT_3.setLastName("Durand");
		CONTACT_3.setUser(USER_2);
		
		USERTOKEN_1.setToken("AZERTYUIOPQSDFGHJKLMWXCVBN123456");
		USERTOKEN_1.setUser(USER_1);
		USERTOKEN_1.setTypeId(UserToken.TYPE_EMAILCONFIRMATION);
		USERTOKEN_1.setExpirationTime(getDate(2032, 1, 1, 12, 12, 12));
		
		PHONE_NUMBER_1.setNumber("212 123 4567");
		PHONE_NUMBER_1.setContact(CONTACT_1);
		PHONE_NUMBER_1.setType(PhoneNumber.TYPE_HOME);
		PHONE_NUMBER_2.setNumber("917 123 4567");
		PHONE_NUMBER_2.setContact(CONTACT_1);
		PHONE_NUMBER_2.setType(PhoneNumber.TYPE_MOBILE);
		PHONE_NUMBER_3.setNumber("818 123 4567");
		PHONE_NUMBER_3.setContact(CONTACT_2);
		PHONE_NUMBER_3.setType(PhoneNumber.TYPE_MOBILE);
		PHONE_NUMBER_4.setNumber("212 987 1234");
		PHONE_NUMBER_4.setContact(CONTACT_3);
		PHONE_NUMBER_4.setType(PhoneNumber.TYPE_HOME);
	}
	
	static private Date getDate(int year, int month, int day, int hour, int minutes, int seconds) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, hour, minutes, seconds);
		return c.getTime();
	}
}
