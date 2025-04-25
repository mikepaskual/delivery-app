package com.mikepaskual.delivery.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueUsernameValidator.class})
public @interface UniqueUsername {

    String message() default "{register.validation.username.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
