package com.mikepaskual.delivery.truck.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {YearRangeValidator.class})
public @interface YearRange {

    String message() default "{truck.validation.year.range}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
