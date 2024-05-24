package com.example.usertrack.customvalidation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE}) //can be applied to types(classes, interfaces, enums) and fields(variables)
@Retention(RUNTIME)
@Constraint(validatedBy = UserRequestValidator.class) //logic elsewhere specified
@Documented
public @interface ValidMobileNumber {
    String message() default "Invalid mobile number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}