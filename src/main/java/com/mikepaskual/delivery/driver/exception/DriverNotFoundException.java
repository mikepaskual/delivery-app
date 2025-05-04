package com.mikepaskual.delivery.driver.exception;

public class DriverNotFoundException extends RuntimeException {

    public DriverNotFoundException(Long driverId) {
        super("Driver not found with ID: " + driverId);
    }
}
