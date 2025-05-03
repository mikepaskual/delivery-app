package com.mikepaskual.delivery.truck.exception;

public class TruckNotFoundException extends RuntimeException {

    public TruckNotFoundException(Long truckId) {
        super("Truck not found with ID: " + truckId);
    }
}
