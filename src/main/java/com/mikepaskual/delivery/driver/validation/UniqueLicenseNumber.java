package com.mikepaskual.delivery.driver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueLicenseNumberValidator.class})
public @interface UniqueLicenseNumber {

    String message() default "{driver.validation.licenseNumber.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
