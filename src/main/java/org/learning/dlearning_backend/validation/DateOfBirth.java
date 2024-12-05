package org.learning.dlearning_backend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.learning.dlearning_backend.validation.validator.DateOfBirthValidator;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateOfBirthValidator.class)
public @interface DateOfBirth {
    String message() default "Date of birth must be greater than 1950 and less than current date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
