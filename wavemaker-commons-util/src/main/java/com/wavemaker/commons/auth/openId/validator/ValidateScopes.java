package com.wavemaker.commons.auth.openId.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, PARAMETER, ANNOTATION_TYPE ,TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ScopesValidator.class)
@Documented
public @interface ValidateScopes {
    String message() default "scopes should not contain spaces";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}