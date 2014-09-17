package com.jeromesimmonds.phonebook.core.dao;

import org.springframework.data.domain.Pageable;

/**
 * @author Jerome Simmonds
 *
 */
public interface FindSpecification {

	public Pageable toPageable();
}
