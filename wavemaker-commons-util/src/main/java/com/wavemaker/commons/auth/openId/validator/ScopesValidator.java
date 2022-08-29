package com.wavemaker.commons.auth.openId.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class ScopesValidator implements ConstraintValidator<ValidateScopes, List<String>> {
    @Override
    public void initialize(ValidateScopes validateScopes) {
    }
    @Override
    public boolean isValid(List<String> scopes, ConstraintValidatorContext constraintValidatorContext) {
        for(String scope:scopes){
            if(scope != null && scope.contains(" ")){
                return false;
            }
        }
        return true;
    }
}
