package com.jeromesimmonds.phonebook.web.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Jerome Simmonds
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
@NotBlank(message="{com.jeromesimmonds.phonebook.constraints.email.required}")
@Email(message="{com.jeromesimmonds.phonebook.constraints.email.invalid}")
@Constraint(validatedBy = EmailAvailableValidator.class)
@Documented
public @interface EmailAvailable {

	String message() default "{com.jeromesimmonds.phonebook.constraints.email.notavailable}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
