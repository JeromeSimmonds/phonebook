package com.jeromesimmonds.phonebook.core.be;

/**
 * @author Jerome Simmonds
 *
 */
public enum AuthorityType {
	
	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_USER("ROLE_USER");
	
	private String value;
	
	private AuthorityType(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
