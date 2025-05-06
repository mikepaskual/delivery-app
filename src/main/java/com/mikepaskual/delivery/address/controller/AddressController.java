package com.mikepaskual.delivery.address.controller;

import com.mikepaskual.delivery.address.dto.CreateAddressRequest;
import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.service.AddressService;
import com.mikepaskual.delivery.shared.util.MessageUtil;
import com.mikepaskual.delivery.user.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.Locale;

@Controller
public class AddressController {

    @Autowired
    private final AddressService addressService;
    @Autowired
    private final MessageUtil message;

    public AddressController(AddressService addressService, MessageUtil message) {
        this.addressService = addressService;
        this.message = message;
    }

    @GetMapping("/addresses")
    public String showAddressesView(Model model, @AuthenticationPrincipal User authenticatedUser) {
        model.addAttribute("addresses", addressService.getAddressesByUser(authenticatedUser.getId())
                .stream()
                .sorted(Comparator.comparing(Address::isMain).reversed())
                .toList());
        return "address/addresses";
    }

    @GetMapping("/addresses/{addressId}")
    public String showAddressView(Model model, @PathVariable Long addressId) {
        Address address = addressService.getAddressOrThrow(addressId);
        model.addAttribute("addressForm", CreateAddressRequest.of(address));
        model.addAttribute("isMain", address.isMain());
        model.addAttribute("addressId", addressId);
        return "address/address";
    }

    @GetMapping("/addresses/new")
    public String showNewAddressForm(Model model) {
        model.addAttribute("addressForm", CreateAddressRequest.builder().build());
        return "address/address-form";
    }

    @PostMapping("/addresses")
    public String processNewAddressForm(@AuthenticationPrincipal User authenticatedUser,
                                        @Valid @ModelAttribute("addressForm") CreateAddressRequest request,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            return "address/address-form";
        }
        addressService.registerAddress(authenticatedUser.getId(), request);
        redirectAttributes.addFlashAttribute("successMessage",
                message.get("address.added.success", locale));
        return "redirect:/addresses";
    }

    @PostMapping("/addresses/{addressId}")
    public String processUpdate(@PathVariable Long addressId, @AuthenticationPrincipal User authenticatedUser,
                                RedirectAttributes redirectAttributes, Locale locale) {
        String messageCode = addressService.getAddressOrThrow(addressId).isMain()
                ? "address.unmarkMain.success" : "address.markMain.success";
        addressService.update(addressId, authenticatedUser.getId());
        redirectAttributes.addFlashAttribute("successMessage", message.get(messageCode, locale));
        return "redirect:/addresses";
    }

}
