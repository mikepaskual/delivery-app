package com.mikepaskual.delivery.driver.service;

import com.mikepaskual.delivery.driver.exception.DriverNotFoundException;
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

    public Driver update(Long driverId, UpdateDriverRequest request) {
        Driver driver = getDriverOrThrow(driverId);
        driver.setAvailableFrom(request.getAvailableFrom());
        driver.setAvailableTo(request.getAvailableTo());
        driver.setLicenseNumber(request.getLicenseNumber());
        return driverRepository.save(driver);
    }

    public Driver getDriverOrThrow(Long driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(driverId));
    }
}
