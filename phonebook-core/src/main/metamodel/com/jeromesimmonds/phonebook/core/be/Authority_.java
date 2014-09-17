package com.jeromesimmonds.phonebook.core.be;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Authority.class)
public abstract class Authority_ {

	public static volatile SingularAttribute<Authority, Integer> id;
	public static volatile SingularAttribute<Authority, Date> created;
	public static volatile SingularAttribute<Authority, AuthorityType> type;
	public static volatile SingularAttribute<Authority, Date> modified;

}

