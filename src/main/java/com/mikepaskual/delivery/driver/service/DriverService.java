package com.mikepaskual.delivery.driver.service;

import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.model.DriverRepository;
import com.mikepaskual.delivery.user.model.User;
import com.mikepaskual.delivery.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver findById(Long userId) {
        return driverRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Driver not found with id: " + userId));
    }
}
