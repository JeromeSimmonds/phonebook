package com.jeromesimmonds.phonebook.core.be;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserAuthority.class)
public abstract class UserAuthority_ {

	public static volatile SingularAttribute<UserAuthority, Integer> id;
	public static volatile SingularAttribute<UserAuthority, Authority> authority;
	public static volatile SingularAttribute<UserAuthority, Date> created;
	public static volatile SingularAttribute<UserAuthority, User> user;
	public static volatile SingularAttribute<UserAuthority, Date> modified;

}

