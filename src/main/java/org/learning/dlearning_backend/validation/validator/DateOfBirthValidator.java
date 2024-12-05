package org.learning.dlearning_backend.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.learning.dlearning_backend.validation.DateOfBirth;

import java.time.LocalDate;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
       if(date == null) {
           return true;
       }
       LocalDate minTime = LocalDate.of(1950, 1, 1);
       LocalDate now = LocalDate.now();
       return date.isAfter(minTime) && date.isBefore(now);
    }
}
