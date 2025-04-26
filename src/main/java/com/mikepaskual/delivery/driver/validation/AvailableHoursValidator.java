package com.mikepaskual.delivery.driver.validation;

import com.mikepaskual.delivery.driver.dto.UpdateDriverRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class AvailableHoursValidator implements ConstraintValidator<AvailableHours, UpdateDriverRequest> {

    @Override
    public boolean isValid(UpdateDriverRequest driverDTO, ConstraintValidatorContext context) {
        boolean isValid = driverDTO.getAvailableTo().isAfter(driverDTO.getAvailableFrom());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("availableTo")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
