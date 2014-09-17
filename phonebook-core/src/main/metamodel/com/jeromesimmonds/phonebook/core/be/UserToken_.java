package com.jeromesimmonds.phonebook.core.be;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserToken.class)
public abstract class UserToken_ {

	public static volatile SingularAttribute<UserToken, Date> expirationTime;
	public static volatile SingularAttribute<UserToken, Date> created;
	public static volatile SingularAttribute<UserToken, String> email;
	public static volatile SingularAttribute<UserToken, String> token;
	public static volatile SingularAttribute<UserToken, User> user;
	public static volatile SingularAttribute<UserToken, Integer> typeId;

}

