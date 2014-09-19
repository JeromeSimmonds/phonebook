package com.jeromesimmonds.phonebook.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeromesimmonds.phonebook.core.bo.UserBO;

/**
 * @author Jerome Simmonds
 *
 */
public class EmailAvailableValidator implements ConstraintValidator<EmailAvailable, String> {

	@Override
	public void initialize(EmailAvailable constraintAnnotation) {	} 

	@Autowired
	private UserBO userBO;
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
		if (email == null) return true;
		return !userBO.emailExists(email);
	}
}
