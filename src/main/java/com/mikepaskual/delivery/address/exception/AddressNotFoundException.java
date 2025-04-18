package com.mikepaskual.delivery.address.exception;

import jakarta.persistence.EntityNotFoundException;

public class AddressNotFoundException extends EntityNotFoundException {

    public AddressNotFoundException(String message) {
      super(message);
    }

    public AddressNotFoundException(Long id) {
      super("Address with ID: %d not found" . formatted(id));
    }
}
