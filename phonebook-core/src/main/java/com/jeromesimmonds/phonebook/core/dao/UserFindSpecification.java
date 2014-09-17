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
import com.jeromesimmonds.phonebook.core.be.User;
import com.jeromesimmonds.phonebook.core.be.User_;

/**
 * @author Jerome Simmonds
 *
 */
public class UserFindSpecification extends AbstractFindSpecification<User> {

	public UserFindSpecification(FindParameters parameters) {
		super(parameters);
	}

	@Override
	protected <S> Expression<S> getField(Root<User> root, Class<S> resultType, FindFilterType type) {
		switch (type) {
			case User:
				return (Expression<S>) root.get(User_.id);
			case Username:
				return (Expression<S>) root.get(User_.username);
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