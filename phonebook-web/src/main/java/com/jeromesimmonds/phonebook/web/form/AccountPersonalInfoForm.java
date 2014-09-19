package com.jeromesimmonds.phonebook.web.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.jeromesimmonds.phonebook.web.validator.UsernameAvailable;

/**
 * @author Jerome Simmonds
 *
 */
public class AccountPersonalInfoForm {

	@NotBlank(message="{com.jeromesimmonds.phonebook.constraints.username.required}")
	@Size(min=2, max=40, message="{com.jeromesimmonds.phonebook.constraints.username.invalid}")
	@Pattern(regexp="^[a-zA-Z0-9_-]+$", message="{com.jeromesimmonds.phonebook.constraints.username.invalid}")
	@UsernameAvailable(message="{com.jeromesimmonds.phonebook.constraints.username.notavailable}")
	private String username;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
