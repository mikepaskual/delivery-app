package com.mikepaskual.delivery.customer.exception;

import jakarta.persistence.EntityNotFoundException;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long customerId) {
        super("Customer not found with ID: " + customerId);
    }
}
