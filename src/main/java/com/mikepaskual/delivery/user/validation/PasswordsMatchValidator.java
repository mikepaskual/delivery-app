package com.mikepaskual.delivery.user.validation;

import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

    private String passwordField;
    private String confirmPasswordField;

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context) {
        try {
            Field password = dto.getClass().getDeclaredField(passwordField);
            Field confirmPassword = dto.getClass().getDeclaredField(confirmPasswordField);
            password.setAccessible(true);
            confirmPassword.setAccessible(true);

            Object passwordValue = password.get(dto);
            Object confirmPasswordValue = confirmPassword.get(dto);

            boolean isEquals = passwordValue.equals(confirmPasswordValue);
            if (!isEquals) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("verifyPassword")
                        .addConstraintViolation();
            }
            return isEquals;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
