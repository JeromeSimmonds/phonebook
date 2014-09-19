package com.jeromesimmonds.phonebook.web.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.jeromesimmonds.phonebook.web.validator.FieldMatch;

/**
 * @author Jerome Simmonds
 *
 */
@FieldMatch.List({
    @FieldMatch(first="newPassword", second="passwordConfirmation", message="{com.jeromesimmonds.phonebook.constraints.passwordconfirmation.match}")
})
public class AccountPasswordForm {

	public static final String FIELD_PASSWORD = "password";
	
	@NotBlank(message="{com.jeromesimmonds.phonebook.constraints.password.required}")
	@Size(min = 8, max = 20, message="{com.jeromesimmonds.phonebook.constraints.password.wrongsize}")
	private String password;
	@NotBlank(message="{com.jeromesimmonds.phonebook.constraints.password.required}")
	@Size(min = 8, max = 20, message="{com.jeromesimmonds.phonebook.constraints.password.wrongsize}")
	private String newPassword;
	@NotBlank(message="{com.jeromesimmonds.phonebook.constraints.passwordconfirmation.required}")
	private String passwordConfirmation;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
}
