package com.mikepaskual.delivery.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class SelectionValueValidator implements ConstraintValidator<SelectionValue, String> {

    @Override
    public boolean isValid(String selectedValue, ConstraintValidatorContext context) {
        return selectedValue != null && !selectedValue.trim().isEmpty();
    }
}
