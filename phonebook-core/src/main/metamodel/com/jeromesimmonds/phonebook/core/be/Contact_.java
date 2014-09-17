package com.jeromesimmonds.phonebook.core.be;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contact.class)
public abstract class Contact_ {

	public static volatile SingularAttribute<Contact, Integer> id;
	public static volatile SingularAttribute<Contact, String> lastName;
	public static volatile SingularAttribute<Contact, Date> created;
	public static volatile SingularAttribute<Contact, String> email;
	public static volatile ListAttribute<Contact, PhoneNumber> phoneNumbers;
	public static volatile SingularAttribute<Contact, String> avatar;
	public static volatile SingularAttribute<Contact, String> firstName;
	public static volatile SingularAttribute<Contact, User> user;
	public static volatile SingularAttribute<Contact, Date> modified;

}

