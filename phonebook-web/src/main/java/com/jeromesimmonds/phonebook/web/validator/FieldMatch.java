package com.jeromesimmonds.phonebook.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Jerome Simmonds
 *
 */
@Constraint(validatedBy = FieldMatchValidator.class)
@Documented
public @interface FieldMatch {
	String message() default "{constraints.fieldmatch}";

	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};

	/**
	 * @return The first field
	 */
	String first();

	/**
	 * @return The second field
	 */
	String second();

	/**
	 * Defines several <code>@FieldMatch</code> annotations on the same element
	 *
	 * @see FieldMatch
	 */
	@Target({TYPE, ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		FieldMatch[] value();
	}
}
