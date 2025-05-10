package com.mikepaskual.delivery.customer.service;

import com.mikepaskual.delivery.customer.dto.ReceiverItem;
import com.mikepaskual.delivery.customer.exception.CustomerNotFoundException;
import com.mikepaskual.delivery.customer.model.Customer;
import com.mikepaskual.delivery.customer.model.CustomerRepository;
import com.mikepaskual.delivery.user.model.RoleRepository;
import com.mikepaskual.delivery.user.model.UserRepository;
import com.mikepaskual.delivery.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository, RoleRepository roleRepository,
                           UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<ReceiverItem> getReceiversExcludingSender(Long customerId) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> UserRole.CUSTOMER.name().equals(role.getName())))
                .filter(user -> !user.getId().equals(customerId))
                .map(user -> ReceiverItem.builder()
                        .setId(user.getId())
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .build())
                .sorted(Comparator.comparing(ReceiverItem::getFirstName)
                        .thenComparing(ReceiverItem::getLastName))
                .toList();
    }

    public Customer getCustomerOrThrow(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}
