package com.jeromesimmonds.phonebook.core;

import com.jeromesimmonds.phonebook.core.be.FindFilter;
import com.jeromesimmonds.phonebook.core.be.FindFilterType;

/**
 * @author Jerome Simmonds
 *
 */
public class FilterNotSupportedException extends FilterException {

	private static final long serialVersionUID = 8907402649219675757L;

	public FilterNotSupportedException(FindFilter<?> filter, FilterProperty property) {
		super(filter, property);		
	}

	public FilterNotSupportedException(FindFilter<?> filter, FilterProperty property, String message, Throwable cause) {
		super(filter, property, message, cause);
	}

	public FilterNotSupportedException(FindFilter<?> filter, FilterProperty property, String message) {
		super(filter, property, message);
	}

	public FilterNotSupportedException(FindFilter<?> filter, FilterProperty property, Throwable cause) {
		super(filter, property, cause);
	}

	public FilterNotSupportedException(FindFilterType type) {
		super(new FindFilter(type), FilterProperty.Type);
	}
}
