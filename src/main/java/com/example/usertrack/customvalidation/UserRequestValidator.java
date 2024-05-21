package com.example.usertrack.customvalidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class UserRequestValidator implements ConstraintValidator<ValidMobileNumber, String> {

    @Override
    public boolean isValid(String mobNum, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(mobNum)) {
            return false;
        }

        // Remove any leading "+" or "0" or country code
        mobNum = mobNum.replaceAll("^(\\+\\d{1,3}[- ]?|0)", "");

        return mobNum.matches("\\d{10}"); //ONLY 10 DIGITS
    }
}