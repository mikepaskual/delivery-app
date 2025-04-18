package com.mikepaskual.delivery.customer.model;

import com.mikepaskual.delivery.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByAddresses(Address address);
}
