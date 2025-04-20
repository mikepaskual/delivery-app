package com.mikepaskual.delivery.customer.service;

import java.util.List;

import com.mikepaskual.delivery.address.exception.AddressNotFoundException;
import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.customer.dto.CreateCustomerRequest;
import com.mikepaskual.delivery.customer.exception.CustomerNotFoundException;
import com.mikepaskual.delivery.customer.model.Customer;
import com.mikepaskual.delivery.customer.model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll(Sort.by("createdAt").descending());
    }

    public Customer registerCustomer(CreateCustomerRequest request) {
        Customer customer = Customer.builder()
                .setAddresses(request.getAddresses())
                .setBirthday(request.getBirthday())
                .setCreatedAt(request.getCreatedAt())
                .setEmail(request.getEmail())
                .setFirstName(request.getFirstName())
                .setGender(request.getGender())
                .setLastName(request.getLastName())
                .setPhone(request.getPhone())
                .build();
        return customerRepository.save(customer);
    }

    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findByAddress(Address address) {
        return customerRepository.findByAddresses(address).orElseThrow(() -> new AddressNotFoundException(address.getId()));
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public Page<Customer> findPaginated(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }


}
