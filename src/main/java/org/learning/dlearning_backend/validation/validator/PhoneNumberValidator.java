package org.learning.dlearning_backend.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.learning.dlearning_backend.validation.PhoneNumber;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String>
{

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        String regex = "^(0[1-9])[0-9]{8,9}$";
        return s.matches(regex);
    }
}
