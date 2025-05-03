package com.mikepaskual.delivery.driver.validation;

import com.mikepaskual.delivery.driver.model.DriverRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueLicenseNumberValidator implements ConstraintValidator<UniqueLicenseNumber, String> {

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public boolean isValid(String licenseNumber, ConstraintValidatorContext context) {
        return !driverRepository.findByLicenseNumber(licenseNumber).isPresent();
    }
}
