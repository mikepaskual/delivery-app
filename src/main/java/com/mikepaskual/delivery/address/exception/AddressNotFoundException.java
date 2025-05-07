package com.mikepaskual.delivery.address.exception;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(Long addressId) {
      super("Address not found with ID: " + addressId);
    }
}
