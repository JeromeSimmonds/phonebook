package com.jeromesimmonds.phonebook.web.validator;

import org.hibernate.validator.constraints.impl.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jerome Simmonds
 *
 */
public class ValidationUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtils.class);
	
	private static final EmailValidator emailValidator = new EmailValidator();
	
	public static boolean isBlank(String value) {
		return value == null || value.trim().length() == 0;
	}
	
	public static boolean isEmailAddress(String email) {
		return emailValidator.isValid(email, null);
	}
}
