package com.mikepaskual.delivery.driver.service;

import com.mikepaskual.delivery.driver.dto.UpdateDriverRequest;
import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.model.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver update(Long userId, UpdateDriverRequest request) {
        Driver driver = findById(userId);
        driver.setAvailableFrom(request.getAvailableFrom());
        driver.setAvailableTo(request.getAvailableTo());
        driver.setLicenseNumber(request.getLicenseNumber());
        return driverRepository.save(driver);
    }

    public Driver findById(Long userId) {
        return driverRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found with ID: " + userId));
    }
}
