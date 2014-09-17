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
import com.jeromesimmonds.phonebook.core.be.PhoneNumber;
import com.jeromesimmonds.phonebook.core.be.PhoneNumber_;

/**
 * @author Jerome Simmonds
 *
 */
public class PhoneNumberFindSpecification extends AbstractFindSpecification<PhoneNumber> {

	public PhoneNumberFindSpecification(FindParameters parameters) {
		super(parameters);
	}

	@Override
	protected <S> Expression<S> getField(Root<PhoneNumber> root, Class<S> resultType, FindFilterType type) {
		switch (type) {
			case Contact:
				return (Expression<S>) root.get(PhoneNumber_.contact);
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