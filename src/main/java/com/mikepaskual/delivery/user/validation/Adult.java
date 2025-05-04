package com.mikepaskual.delivery.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AdultValidator.class})
public @interface Adult {

    String message() default "{profile.validation.birthday.adult}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
