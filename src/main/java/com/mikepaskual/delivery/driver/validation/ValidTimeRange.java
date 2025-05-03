package com.mikepaskual.delivery.driver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidTimeRangeValidator.class})
public @interface ValidTimeRange {

    String message() default "{driver.validation.time.range}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fromTime();
    String toTime();
}
