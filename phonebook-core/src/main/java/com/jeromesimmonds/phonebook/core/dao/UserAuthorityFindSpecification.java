package com.jeromesimmonds.phonebook.core.dao;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort.Order;

import com.jeromesimmonds.phonebook.core.FilterNotSupportedException;
import com.jeromesimmonds.phonebook.core.SortNotSupportedException;
import com.jeromesimmonds.phonebook.core.be.FindFilterType;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.FindSort;
import com.jeromesimmonds.phonebook.core.be.UserAuthority;
import com.jeromesimmonds.phonebook.core.be.UserAuthority_;

/**
 * @author Jerome Simmonds
 *
 */
public class UserAuthorityFindSpecification extends AbstractFindSpecification<UserAuthority> {

	public UserAuthorityFindSpecification(FindParameters pParameters) {
		super(pParameters);
	}

	@Override
	protected <S> Expression<S> getField(Root<UserAuthority> root, Class<S> resultType, FindFilterType type) {
		switch (type) {
			case User:
				return (Expression<S>) root.get(UserAuthority_.user);
			default:
				throw new FilterNotSupportedException(type);
		}
	}
	
	@Override
	protected void applySort(FindSort findSort, List<Order> orders) {
		switch (findSort) {
			default:
				throw new SortNotSupportedException(findSort);
		}
	}
}
