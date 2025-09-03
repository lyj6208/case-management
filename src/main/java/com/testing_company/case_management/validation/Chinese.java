package com.testing_company.case_management.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ ElementType.FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ChineseValidator.class)
public @interface Chinese {
    String message()default "只能輸入中文";
    Class<?>[] groups() default {};
    Class<? extends Payload>[]payload() default{};
}
