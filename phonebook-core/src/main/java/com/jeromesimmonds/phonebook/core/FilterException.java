package com.jeromesimmonds.phonebook.core;

import com.jeromesimmonds.phonebook.core.be.FindFilter;

/**
 * @author Jerome Simmonds
 *
 */
public class FilterException extends CoreException {

	private static final long serialVersionUID = -7648070219802572025L;

	public enum FilterProperty {
		Type,
		Mode,
		Value
	}
	
	private FindFilter<?> filter;
	private FilterProperty property;
	
	public FilterException(FindFilter<?> filter, FilterProperty property) {
		super(filter.toString() + " : " + property.toString());
		this.filter = filter;
		this.property = property;
	}

	public FilterException(FindFilter<?> filter, FilterProperty property, String message, Throwable cause) {
		super(filter.toString() + " : " + property.toString() + ". " + message, cause);
		this.filter = filter;
		this.property = property;
	}
	
	public FilterException(FindFilter<?> filter, FilterProperty property, String message) {
		super(filter.toString() + " : " + property.toString() + ". " + message);
		this.filter = filter;
		this.property = property;
	}

	public FilterException(FindFilter<?> filter, FilterProperty property, Throwable cause) {
		super(filter.toString() + " : " + property.toString(), cause);
		this.filter = filter;
		this.property = property;
	}

	public FindFilter<?> getFilter() {
		return filter;
	}

	public FilterProperty getProperty() {
		return property;
	}
}
