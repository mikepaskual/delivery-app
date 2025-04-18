package com.mikepaskual.delivery.address.controller;

import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.service.AddressService;
import com.mikepaskual.delivery.customer.model.Customer;
import com.mikepaskual.delivery.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AddressController {

    @Autowired
    private final AddressService addressService;
    @Autowired
    private final CustomerService customerService;

    public AddressController(AddressService addressService, CustomerService customerService) {
        this.addressService = addressService;
        this.customerService = customerService;
    }

    @GetMapping("/addresses/{id}/delete")
    public String deleteAddress(@PathVariable Long id, Model model) {
        // TODO: validate who can delete address
        Address address = addressService.markAsHidden(id);
        Customer customer = customerService.findByAddress(address);
        return "redirect:/customers/" + customer.getId();
    }

}
