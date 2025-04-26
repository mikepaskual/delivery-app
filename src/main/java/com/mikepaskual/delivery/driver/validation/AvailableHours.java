package com.mikepaskual.delivery.driver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AvailableHoursValidator.class})
public @interface AvailableHours {

    String message() default "{driver.validation.availableHours}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
