package com.jeromesimmonds.phonebook.core;

import com.jeromesimmonds.phonebook.core.be.Fetch;

/**
 * @author Jerome Simmonds
 *
 */
public class FetchNotSupportedException extends CoreException {

	private static final long serialVersionUID = 6407416965878083654L;
	
	private Fetch fetch;
	
	public FetchNotSupportedException(Fetch fetch) {
		super("Fetch not supported: " + fetch);
		this.fetch = fetch;
	}
	
	public Fetch getFetch() {
		return fetch;
	}
}
