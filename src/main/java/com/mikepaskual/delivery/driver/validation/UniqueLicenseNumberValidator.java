package com.mikepaskual.delivery.driver.validation;

import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.model.DriverRepository;
import com.mikepaskual.delivery.user.model.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueLicenseNumberValidator implements ConstraintValidator<UniqueLicenseNumber, String> {

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public boolean isValid(String licenseNumber, ConstraintValidatorContext context) {
        Optional<Driver> driver = driverRepository.findByLicenseNumber(licenseNumber);
        if (!driver.isPresent()) {
            return true;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User authenticatedUser = (User) principal;
        Long authenticatedUserId = authenticatedUser.getId();
        return driver.get().getId().equals(authenticatedUserId);
    }
}
