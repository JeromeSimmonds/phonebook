package com.jeromesimmonds.phonebook.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeromesimmonds.phonebook.core.bo.UserBO;

/**
 * @author Jerome Simmonds
 *
 */
public class UsernameAvailableValidator implements ConstraintValidator<UsernameAvailable, String> {

	@Override
	public void initialize(UsernameAvailable constraintAnnotation) {	} 

	@Autowired
	private UserBO userBO;
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
		if (username == null) return true;
		return !userBO.usernameExists(username);
	}
}
