package com.jeromesimmonds.phonebook.web.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.jeromesimmonds.phonebook.web.validator.EmailAvailable;
import com.jeromesimmonds.phonebook.web.validator.FieldMatch;
import com.jeromesimmonds.phonebook.web.validator.UsernameAvailable;

/**
 * @author Jerome Simmonds
 *
 */
//@FieldMatch.List({
//    @FieldMatch(first="password", second="passwordConfirmation", message="{com.jeromesimmonds.phonebook.constraints.passwordconfirmation.match}")
//})
public class SignUpForm {

	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_USERNAME = "username";
	public static final String FIELD_PASSWORD = "password";
	
//	@NotBlank(message="{com.jeromesimmonds.phonebook.constraints.email.required}")
//	@Email(message="{com.jeromesimmonds.phonebook.constraints.email.invalid}")
//	@EmailAvailable(message="{com.jeromesimmonds.phonebook.constraints.email.notavailable}")
	private String email;
	
//	@NotBlank(message="{com.jeromesimmonds.phonebook.constraints.username.required}")
//	@Size(min=2, max=40, message="{com.jeromesimmonds.phonebook.constraints.username.invalid}")
//	@Pattern(regexp="^[a-zA-Z0-9_-]+$", message="{com.jeromesimmonds.phonebook.constraints.username.invalid}")
//	@UsernameAvailable(message="{com.jeromesimmonds.phonebook.constraints.username.notavailable}")
	private String username;
	
//	@NotBlank(message="{com.jeromesimmonds.phonebook.constraints.password.required}")
//	@Size(min = 8, max = 20, message="{com.jeromesimmonds.phonebook.constraints.password.wrongsize}")
	private String password;
//	@NotBlank(message="{com.jeromesimmonds.phonebook.constraints.passwordconfirmation.required}")
//	private String passwordConfirmation;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//	public String getPasswordConfirmation() {
//		return passwordConfirmation;
//	}
//	public void setPasswordConfirmation(String passwordConfirmation) {
//		this.passwordConfirmation = passwordConfirmation;
//	}
}
