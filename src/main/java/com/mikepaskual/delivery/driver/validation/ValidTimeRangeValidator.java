package com.mikepaskual.delivery.driver.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalTime;

@Component
public class ValidTimeRangeValidator implements ConstraintValidator<ValidTimeRange, Object> {

    private String from;
    private String to;

    @Override
    public void initialize(ValidTimeRange constraintAnnotation) {
        this.from = constraintAnnotation.fromTime();
        this.to = constraintAnnotation.toTime();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context) {
        try {
            Field startFieldRef = dto.getClass().getDeclaredField(from);
            Field endFieldRef = dto.getClass().getDeclaredField(to);
            startFieldRef.setAccessible(true);
            endFieldRef.setAccessible(true);

            LocalTime startTime = (LocalTime) startFieldRef.get(dto);
            LocalTime endTime = (LocalTime) endFieldRef.get(dto);

            long startMinutes = startTime.toSecondOfDay() / 60;
            long endMinutes = endTime.toSecondOfDay() / 60;

            long diff = endMinutes - startMinutes;
            if (diff <= 0) {
                diff += 24 * 60;
            }

            boolean isValid = diff >= 30 && diff <= 300;

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("availableTo")
                        .addConstraintViolation();
            }
            return isValid;
        } catch (Exception e) {
            return false;
        }
    }
}
