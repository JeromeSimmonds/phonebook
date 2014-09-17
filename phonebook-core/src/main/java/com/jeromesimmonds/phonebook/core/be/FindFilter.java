package com.jeromesimmonds.phonebook.core.be;

/**
 * @author Jerome Simmonds
 *
 */
public class FindFilter<T> {
	
	private static final String END_FIND_FILTER = "]";
	private static final String VALUE = "Value:";
	private static final String MODE = "Mode:";
	private static final String COMMA = ",";
	private static final String TYPE = "Type:";
	private static final String FIND_FILTER = "FindFilter[";
	
	private T value;
	private FindFilterType type;
	private FindFilterMode mode = FindFilterMode.Eq;
	
	public FindFilter() {
		super();
	}
	
	public FindFilter(FindFilterType type) {
		this(type, FindFilterMode.Eq, null);
	}
	
	public FindFilter(FindFilterType type, FindFilterMode mode) {
		this(type, mode, null);
		
	}
	
	public FindFilter(FindFilterType type, FindFilterMode mode, T value) {
		this();
		this.value = value;
		this.type = type;
		this.mode = mode;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public FindFilterType getType() {
		return type;
	}

	public void setType(FindFilterType type) {
		this.type = type;
	}

	public FindFilterMode getMode() {
		return mode;
	}

	public void setMode(FindFilterMode mode) {
		this.mode = mode;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(FIND_FILTER);
		result.append(TYPE).append(getType().name()).append(COMMA);
		result.append(MODE).append(getMode().name()).append(COMMA);
		result.append(VALUE).append(getValue());
		result.append(END_FIND_FILTER);
		return result.toString();
	}
}
