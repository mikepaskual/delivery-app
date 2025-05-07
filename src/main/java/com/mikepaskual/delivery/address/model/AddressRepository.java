package com.mikepaskual.delivery.address.model;

import com.mikepaskual.delivery.customer.model.Customer;
import com.mikepaskual.delivery.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);

    List<Address> findByUserAndDeletedFalse(User user);
}
