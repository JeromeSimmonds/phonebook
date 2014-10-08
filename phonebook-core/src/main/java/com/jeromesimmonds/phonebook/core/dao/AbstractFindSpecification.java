package com.jeromesimmonds.phonebook.core.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import com.jeromesimmonds.phonebook.core.FilterException.FilterProperty;
import com.jeromesimmonds.phonebook.core.FilterNotSupportedException;
import com.jeromesimmonds.phonebook.core.be.FindFilter;
import com.jeromesimmonds.phonebook.core.be.FindFilterMode;
import com.jeromesimmonds.phonebook.core.be.FindFilterType;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.FindSort;

/**
 * @author Jerome Simmonds
 *
 * @param <T> Entity type
 */
public abstract class AbstractFindSpecification<T> implements Specification<T>, FindSpecification {
	
	private FindParameters parameters;
	
	public AbstractFindSpecification(FindParameters parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public Pageable toPageable() {
		int pageSize = Math.abs(parameters.getTo() - parameters.getFrom()) + 1;
    	int pageNumber = (parameters.getFrom() == parameters.getTo()) ? parameters.getFrom() - 1 : parameters.getFrom() / pageSize;
    	Sort s = applySorts(parameters.getSorts());
    	PageRequest result = new PageRequest(pageNumber, pageSize, s);
    	return result;
	}

	private Sort applySorts(List<FindSort> sorts) {
		if (sorts == null || sorts.isEmpty()) return null;
		List<Order> orders = new ArrayList<Sort.Order>();
		for (FindSort s : sorts) {
			applySort(s, orders);
		}
		return new Sort(orders);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Predicate predicate = builder.isTrue(builder.literal(Boolean.TRUE));
		for (FindFilter<?> f : parameters.getFilters()) {
			predicate = builder.and(predicate, applyFilter(root, builder, f));
		}
		return predicate;
	}

	private Predicate applyFilter(Root<T> root, CriteriaBuilder builder, FindFilter filter) {
		switch (filter.getMode()) {
			case Eq:
				return builder.equal(getField(root, Object.class, filter.getType()), filter.getValue());
			case GreaterOrEq:
				Object oGEValue = filter.getValue();
				if (oGEValue instanceof Date) {
					return builder.greaterThanOrEqualTo(getField(root, Date.class, filter.getType()), (Date) filter.getValue());
				} else {
					return builder.ge(getField(root, Number.class, filter.getType()), (Number) filter.getValue());
				}
			case GreaterThan:
				Object oGTValue = filter.getValue();
				if (oGTValue instanceof Date) {
					return builder.greaterThan(getField(root, Date.class, filter.getType()), (Date) filter.getValue());
				} else {
					return builder.gt(getField(root, Number.class, filter.getType()), (Number) filter.getValue());
				}			
			case In:
				return getField(root, Collection.class, filter.getType()).in(getValues(filter));
			case IsNotNull:
				return builder.isNotNull(getField(root, Object.class, filter.getType()));
			case IsNull:
				return builder.isNull(getField(root, Object.class, filter.getType()));
			case LessOrEq:
				Object oLEValue = filter.getValue();
				if (oLEValue instanceof Date) {
					return builder.lessThanOrEqualTo(getField(root, Date.class, filter.getType()), (Date) oLEValue);
				} else {
					return builder.le(getField(root, Number.class, filter.getType()), (Number) oLEValue);
				}
			case LessThan:
				Object oLTValue = filter.getValue();
				if (oLTValue instanceof Date) {
					return builder.lessThan(getField(root, Date.class, filter.getType()), (Date) filter.getValue());
				} else {
					return builder.lt(getField(root, Number.class, filter.getType()), (Number) filter.getValue());
				}
			case Like:
				return builder.like(getField(root, String.class, filter.getType()), (String) filter.getValue());
			case NotEq:
				return builder.notEqual(getField(root, Object.class, filter.getType()), filter.getValue());
			case NotIn:
				return builder.not(getField(root, Collection.class, filter.getType()).in(filter.getValue()));
			case NotLike:
				return builder.like(getField(root, String.class, filter.getType()), (String) filter.getValue());
			default:
				throw new FilterNotSupportedException(filter, FilterProperty.Mode);
		}
	}

	protected Collection<?> getValues(FindFilter filter) {
		List<Object> values = new ArrayList<Object>();
		Collection<?> filterValues = (Collection<?>) filter.getValue();
		if (filterValues == null) return values;
		if (filterValues.isEmpty()) return filterValues;
		FindFilter<Object> sameTypeFilter = new FindFilter<Object>(filter.getType(), FindFilterMode.Eq);
		for (Object o : filterValues) {
			sameTypeFilter.setValue(o);
			values.add(sameTypeFilter.getValue());
		}
		return values;
	}
	
	public FindParameters getParameters() {
		return parameters;
	}

	protected abstract <S> Expression<S> getField(Root<T> root, Class<S> resultType, FindFilterType type);
	
	protected abstract void applySort(FindSort findSort, List<Order> orders);
}
