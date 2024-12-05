package org.learning.dlearning_backend.validation;

import jakarta.validation.Constraint;
import org.learning.dlearning_backend.validation.validator.DateOfBirthValidator;
import org.learning.dlearning_backend.validation.validator.PhoneNumberValidator;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {
    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
