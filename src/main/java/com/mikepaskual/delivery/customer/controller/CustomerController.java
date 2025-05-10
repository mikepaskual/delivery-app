package com.mikepaskual.delivery.customer.controller;

import com.mikepaskual.delivery.customer.service.CustomerService;
import com.mikepaskual.delivery.shared.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {

    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final MessageUtil messageUtil;

    public CustomerController(CustomerService customerService, MessageUtil messageUtil) {
        this.customerService = customerService;
        this.messageUtil = messageUtil;
    }
}
