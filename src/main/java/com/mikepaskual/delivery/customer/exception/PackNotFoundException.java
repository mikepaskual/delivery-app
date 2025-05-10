package com.mikepaskual.delivery.customer.exception;

public class PackNotFoundException extends RuntimeException {

    public PackNotFoundException(Long packId) {
        super("Pack not found with ID: " + packId);
    }
}
