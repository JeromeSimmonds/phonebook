package com.jeromesimmonds.phonebook.web.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.jeromesimmonds.phonebook.web.validator.EmailAvailable;
import com.jeromesimmonds.phonebook.web.validator.FieldMatch;

/**
 * @author Jerome Simmonds
 *
 */
@FieldMatch.List({
    @FieldMatch(first="email", second="emailConfirmation", message="{com.jeromesimmonds.phonebook.constraints.emailconfirmation.match}")
})
public class AccountEmailForm {

	@NotBlank(message="{com.jeromesimmonds.phonebook.constraints.email.required}")
	@Email(message="{com.jeromesimmonds.phonebook.constraints.email.invalid}")
	@EmailAvailable(message="{com.jeromesimmonds.phonebook.constraints.email.notavailable}")
	private String email;
	private String emailConfirmation;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailConfirmation() {
		return emailConfirmation;
	}
	public void setEmailConfirmation(String emailConfirmation) {
		this.emailConfirmation = emailConfirmation;
	}
}
