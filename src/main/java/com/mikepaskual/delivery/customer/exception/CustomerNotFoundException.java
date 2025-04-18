package com.mikepaskual.delivery.customer.exception;

import jakarta.persistence.EntityNotFoundException;

public class CustomerNotFoundException extends EntityNotFoundException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Long id) {
        super("Customer with ID: %d not found" . formatted(id));
    }
}
