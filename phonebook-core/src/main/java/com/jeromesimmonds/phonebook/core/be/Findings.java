package com.jeromesimmonds.phonebook.core.be;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerome Simmonds
 *
 */
public class Findings<T> {
	
	private List<T> results = new ArrayList<T>();
	private int totalAvailable = 0;
	
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	public int getTotalAvailable() {
		return totalAvailable;
	}
	public void setTotalAvailable(int totalAvailable) {
		this.totalAvailable = totalAvailable;
	}
}
