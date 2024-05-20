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


        mobNum = mobNum.replaceAll("^(\\+\\d{1,3}[- ]?|0)", ""); //regex to remove country code or 0 prefix

        return mobNum.matches("\\d{10}"); //sends only 10 digits to be added
    }
}