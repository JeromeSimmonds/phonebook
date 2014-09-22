package com.jeromesimmonds.phonebook.web.be;

import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.web.Constants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONContact {

	@JsonProperty(Constants.ID)
	private int id;
	
	public JSONContact(int id) {
		super();
		this.id = id;
	}

	public JSONContact(Contact coreContact) {
		super();
		this.id = coreContact.getId();
	}

	public long getId() { return id; }
	public void setId(int id) { this.id = id; }
}
