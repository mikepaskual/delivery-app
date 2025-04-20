package com.mikepaskual.delivery.customer.controller;

import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.service.AddressService;
import com.mikepaskual.delivery.customer.model.Customer;
import com.mikepaskual.delivery.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final AddressService addressService;

    public CustomerController(CustomerService customerService, AddressService addressService) {
        this.addressService = addressService;
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String showCustomers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerService.findPaginated(pageable);
        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        return "customer/customers";
    }

    @GetMapping("/customers/{id}")
    public String editOrViewCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("addresses", addressService.findNotHiddenByCustomer(customer));
        return "customer/customer";
    }

}
