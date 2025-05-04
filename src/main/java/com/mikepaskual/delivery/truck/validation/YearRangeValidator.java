package com.mikepaskual.delivery.truck.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class YearRangeValidator implements ConstraintValidator<YearRange, Integer> {

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        return year != null && year.intValue() >= 1950 && year.intValue() <= LocalDate.now().getYear();
    }
}
