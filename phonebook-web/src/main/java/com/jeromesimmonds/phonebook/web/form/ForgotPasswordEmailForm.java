package com.jeromesimmonds.phonebook.web.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Jerome Simmonds
 *
 */
public class ForgotPasswordEmailForm {

	@NotBlank(message="{com.jeromesimmonds.phonebook.constraints.email.required}")
	@Email(message="{com.jeromesimmonds.phonebook.constraints.email.invalid}")
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
