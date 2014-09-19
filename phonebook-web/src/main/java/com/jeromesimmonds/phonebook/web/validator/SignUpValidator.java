package com.jeromesimmonds.phonebook.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jeromesimmonds.phonebook.core.bo.UserBO;
import com.jeromesimmonds.phonebook.web.Constants;
import com.jeromesimmonds.phonebook.web.form.SignUpForm;

/**
 * @author Jerome Simmonds
 *
 */
@Component
public class SignUpValidator implements Validator {

	@Autowired
	private UserBO mUserBO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(SignUpForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SignUpForm form = (SignUpForm) target;
		
		// Email
		// ValidationUtils.rejectIfEmpty(errors, "email", "com.jeromesimmonds.phonebook.constraints.email.required");
		if (ValidationUtils.isBlank(form.getEmail()))
			errors.rejectValue(SignUpForm.FIELD_EMAIL, "com.jeromesimmonds.phonebook.constraints.email.required");
		else if (!ValidationUtils.isEmailAddress(form.getEmail()))
			errors.rejectValue(SignUpForm.FIELD_EMAIL, "com.jeromesimmonds.phonebook.constraints.email.invalid");
		else if (mUserBO.emailExists(form.getEmail()))
			errors.rejectValue(SignUpForm.FIELD_EMAIL, "com.jeromesimmonds.phonebook.constraints.email.notavailable");
		
		// Username
		form.setUsername(form.getUsername().trim());
		if (ValidationUtils.isBlank(form.getUsername()))
			errors.rejectValue(SignUpForm.FIELD_USERNAME, "com.jeromesimmonds.phonebook.constraints.username.required");
		else if (form.getUsername().length() < 2 || form.getUsername().length() > 40)
			errors.rejectValue(SignUpForm.FIELD_USERNAME, "com.jeromesimmonds.phonebook.constraints.username.invalid");
		else if (mUserBO.usernameExists(form.getUsername()))
			errors.rejectValue(SignUpForm.FIELD_USERNAME, "com.jeromesimmonds.phonebook.constraints.username.notavailable");
		
		// Password
		form.setPassword(form.getPassword().trim());
		if (ValidationUtils.isBlank(form.getPassword()))
			errors.rejectValue(SignUpForm.FIELD_PASSWORD, "com.jeromesimmonds.phonebook.constraints.password.required");
		else if (form.getPassword().length() < 8 || form.getPassword().length() > 20)
			errors.rejectValue(SignUpForm.FIELD_PASSWORD, "com.jeromesimmonds.phonebook.constraints.password.wrongsize");
	}
}