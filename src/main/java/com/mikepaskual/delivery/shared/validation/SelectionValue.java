package com.mikepaskual.delivery.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SelectionValueValidator.class})
public @interface SelectionValue {

    String message() default "{option.select.validation.notEmpty}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
