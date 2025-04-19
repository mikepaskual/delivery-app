package com.mikepaskual.delivery.address.controller;

import com.mikepaskual.delivery.address.dto.CreateAddressRequest;
import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.service.AddressService;
import com.mikepaskual.delivery.customer.model.Customer;
import com.mikepaskual.delivery.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class AddressController {

    @Autowired
    private final AddressService addressService;
    @Autowired
    private final CustomerService customerService;
    @Autowired
    private MessageSource messageSource;

    public AddressController(AddressService addressService, CustomerService customerService, MessageSource messageSource) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.messageSource = messageSource;
    }

    @GetMapping("/customers/{id}/address/new")
    public String showNewAddressForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        CreateAddressRequest addressForm = CreateAddressRequest.builder()
                .setCustomer(customer).build();
        model.addAttribute("addressForm", addressForm);
        return "address/address-form";
    }

    @PostMapping("/customers/{id}/address/submit")
    public String saveNewAddressToCustomer(@PathVariable Long id, Model model,
                                           @ModelAttribute("newAddress") CreateAddressRequest request,
                                           BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes, Locale locale) {
        Customer customer = customerService.findById(request.getCustomer().getId());
        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", customer);
            return "address/address-form";
        }
        request.setCustomer(customer);
        addressService.registerAddress(request);
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage("address.added.success", null, locale));
        return "redirect:/customers/" + customer.getId();
    }

    @PostMapping("/addresses/{id}/delete")
    public String deleteAddressOfCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes, Locale locale) {
        // TODO: validate who can delete address
        Address address = addressService.markAsHidden(id);
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage("address.deleted.success", null, locale));
        return "redirect:/customers/" + address.getCustomer().getId();
    }

    @PostMapping("/customers/{customerId}/addresses/{addressId}/mark-as-main")
    public String markAddressAsMainOfCustomer(@PathVariable Long customerId, @PathVariable Long addressId, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        Address address = addressService.findById(addressId);
        if (!address.getCustomer().getId().equals(customerId)) {
            throw new IllegalArgumentException("La dirección no pertenece al cliente especificado");
        }
        addressService.markAsMain(addressId);
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage("address.markMain.success", null, locale));
        return "redirect:/customers/" + address.getCustomer().getId();
    }

    @PostMapping("/customers/{customerId}/addresses/{addressId}/unmark-as-main")
    public String unmarkAddressAsMainOfCustomer(@PathVariable Long customerId, @PathVariable Long addressId, RedirectAttributes redirectAttributes, Locale locale) {
        Address address = addressService.findById(addressId);
        if (!address.getCustomer().getId().equals(customerId)) {
            throw new IllegalArgumentException("La dirección no pertenece al cliente especificado");
        }
        addressService.unmarkAsMain(addressId);
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage("address.unmarkMain.success", null, locale));
        return "redirect:/customers/" + address.getCustomer().getId();
    }

}
