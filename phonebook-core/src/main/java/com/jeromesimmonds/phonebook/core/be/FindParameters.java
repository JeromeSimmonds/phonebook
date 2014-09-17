package com.jeromesimmonds.phonebook.core.be;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerome Simmonds
 *
 */
public class FindParameters {
	
	private int from = 1;
	private int to = 1;
	private List<FindSort> sorts = new ArrayList<FindSort>();
	private List<FindFilter<?>> filters = new ArrayList<FindFilter<?>>();
	private List<Fetch> fetchGroup = new ArrayList<Fetch>();
	
	public FindParameters() {
		super();
	}
	
	public FindParameters(int from, int to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public List<FindSort> getSorts() {
		return sorts;
	}
	public void setSorts(List<FindSort> sorts) {
		this.sorts = sorts;
	}
	public List<FindFilter<?>> getFilters() {
		return filters;
	}
	public void setFilters(List<FindFilter<?>> filters) {
		this.filters = filters;
	}
	public List<Fetch> getFetchGroup() {
		return fetchGroup;
	}
	public void setFetchGroup(List<Fetch> fetchGroup) {
		this.fetchGroup = fetchGroup;
	}
	
	public <T> FindParameters with(FindFilterType type, FindFilterMode mode, T value) {
		if (getFilters() == null) setFilters(new ArrayList<FindFilter<?>>());
		getFilters().add(new FindFilter<T>(type, mode, value));
		return this;
	}
	
	public <T> FindParameters with(FindFilterType type, T value) {
		if (getFilters() == null) setFilters(new ArrayList<FindFilter<?>>());
		getFilters().add(new FindFilter<T>(type, FindFilterMode.Eq, value));
		return this;
	}
	
	public FindParameters with(FindSort sort) {
		sorts.add(sort);
		return this;
	}
}
