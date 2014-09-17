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
		int oPageSize = Math.abs(parameters.getTo() - parameters.getFrom()) + 1;
    	int oPageNumber = (parameters.getFrom() == parameters.getTo()) ? parameters.getFrom() - 1 : parameters.getFrom() / oPageSize;
    	Sort s = applySorts(parameters.getSorts());
    	PageRequest oResult = new PageRequest(oPageNumber, oPageSize, s);
    	return oResult;
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
		Predicate oPredicate = builder.isTrue(builder.literal(Boolean.TRUE));
		for (FindFilter<?> f : parameters.getFilters()) {
			oPredicate = builder.and(oPredicate, applyFilter(root, builder, f));
		}
		return oPredicate;
	}

	private Predicate applyFilter(Root<T> pRoot, CriteriaBuilder pBuilder, FindFilter filter) {
		switch (filter.getMode()) {
			case Eq:
				return pBuilder.equal(getField(pRoot, Object.class, filter.getType()), filter.getValue());
			case GreaterOrEq:
				Object oGEValue = filter.getValue();
				if (oGEValue instanceof Date) {
					return pBuilder.greaterThanOrEqualTo(getField(pRoot, Date.class, filter.getType()), (Date) filter.getValue());
				} else {
					return pBuilder.ge(getField(pRoot, Number.class, filter.getType()), (Number) filter.getValue());
				}
			case GreaterThan:
				Object oGTValue = filter.getValue();
				if (oGTValue instanceof Date) {
					return pBuilder.greaterThan(getField(pRoot, Date.class, filter.getType()), (Date) filter.getValue());
				} else {
					return pBuilder.gt(getField(pRoot, Number.class, filter.getType()), (Number) filter.getValue());
				}			
			case In:
				return getField(pRoot, Collection.class, filter.getType()).in(getValues(filter));
			case IsNotNull:
				return pBuilder.isNotNull(getField(pRoot, Object.class, filter.getType()));
			case IsNull:
				return pBuilder.isNull(getField(pRoot, Object.class, filter.getType()));
			case LessOrEq:
				Object oLEValue = filter.getValue();
				if (oLEValue instanceof Date) {
					return pBuilder.lessThanOrEqualTo(getField(pRoot, Date.class, filter.getType()), (Date) oLEValue);
				} else {
					return pBuilder.le(getField(pRoot, Number.class, filter.getType()), (Number) oLEValue);
				}
			case LessThan:
				Object oLTValue = filter.getValue();
				if (oLTValue instanceof Date) {
					return pBuilder.lessThan(getField(pRoot, Date.class, filter.getType()), (Date) filter.getValue());
				} else {
					return pBuilder.lt(getField(pRoot, Number.class, filter.getType()), (Number) filter.getValue());
				}
			case Like:
				return pBuilder.like(getField(pRoot, String.class, filter.getType()), (String) filter.getValue());
			case NotEq:
				return pBuilder.notEqual(getField(pRoot, Object.class, filter.getType()), filter.getValue());
			case NotIn:
				return pBuilder.not(getField(pRoot, Collection.class, filter.getType()).in(filter.getValue()));
			case NotLike:
				return pBuilder.like(getField(pRoot, String.class, filter.getType()), (String) filter.getValue());
			default:
				throw new FilterNotSupportedException(filter, FilterProperty.Mode);
		}
	}

	protected Collection<?> getValues(FindFilter filter) {
		List<Object> values = new ArrayList<Object>();
		Collection<?> filterValues = (Collection<?>) filter.getValue();
		if (filterValues == null) return values;
		if (filterValues.isEmpty()) return filterValues;
		FindFilter<Object> oSameTypeFilter = new FindFilter<Object>(filter.getType(), FindFilterMode.Eq);
		for (Object o : filterValues) {
			oSameTypeFilter.setValue(o);
			values.add(oSameTypeFilter.getValue());
		}
		return values;
	}
	
	public FindParameters getParameters() {
		return parameters;
	}

	protected abstract <S> Expression<S> getField(Root<T> pRoot, Class<S> pResultType, FindFilterType pType);
	
	protected abstract void applySort(FindSort pFindSort, List<Order> pOrders);
}
