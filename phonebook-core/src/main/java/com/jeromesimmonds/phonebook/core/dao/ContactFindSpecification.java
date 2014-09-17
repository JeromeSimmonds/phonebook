package com.jeromesimmonds.phonebook.core.dao;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.jeromesimmonds.phonebook.core.FilterNotSupportedException;
import com.jeromesimmonds.phonebook.core.SortNotSupportedException;
import com.jeromesimmonds.phonebook.core.be.FindFilterType;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.FindSort;
import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.be.Contact_;

/**
 * @author Jerome Simmonds
 *
 */
public class ContactFindSpecification extends AbstractFindSpecification<Contact> {

	public ContactFindSpecification(FindParameters parameters) {
		super(parameters);
	}

	@Override
	protected <S> Expression<S> getField(Root<Contact> root, Class<S> resultType, FindFilterType type) {
		switch (type) {
			case Contact:
				return (Expression<S>) root.get(Contact_.id);
			case User:
				return (Expression<S>) root.get(Contact_.user);
			case FirstName:
				return (Expression<S>) root.get(Contact_.firstName);
			case LastName:
				return (Expression<S>) root.get(Contact_.lastName);
			default:
				throw new FilterNotSupportedException(type);
			}
	}

	@Override
	protected void applySort(FindSort findSort, List<Order> orders) {
		switch (findSort) {
			case Alpha:
				orders.add(new Order(Sort.Direction.ASC, Contact_.lastName.getName()));
			case RevAlpha:
				orders.add(new Order(Sort.Direction.DESC, Contact_.lastName.getName()));
			case MostRecent:
				orders.add(new Order(Sort.Direction.ASC, Contact_.id.getName()));
			default:
				throw new SortNotSupportedException(findSort);
		}
	}
}
