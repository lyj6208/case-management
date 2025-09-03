package com.testing_company.case_management.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ChineseValidator implements ConstraintValidator<Chinese, String> {
    public static final String CHINESE_PATTERN="^[\\u4e00-\\u9fa5]+$";
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if(value==null)return true;
        return value.matches(CHINESE_PATTERN);
    }
}
