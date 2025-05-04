package com.mikepaskual.delivery.truck.controller;

import com.mikepaskual.delivery.driver.service.DriverService;
import com.mikepaskual.delivery.shared.util.MessageUtil;
import com.mikepaskual.delivery.truck.dto.CreateTruckRequest;
import com.mikepaskual.delivery.truck.model.FuelType;
import com.mikepaskual.delivery.truck.model.StatusTruck;
import com.mikepaskual.delivery.truck.model.Transmission;
import com.mikepaskual.delivery.truck.model.Truck;
import com.mikepaskual.delivery.truck.service.TruckService;
import com.mikepaskual.delivery.user.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class TruckController {

    @Autowired
    private final DriverService driverService;
    @Autowired
    private final MessageUtil message;
    @Autowired
    private final TruckService truckService;

    public TruckController(DriverService driverService, MessageUtil message, TruckService truckService) {
        this.driverService = driverService;
        this.message = message;
        this.truckService = truckService;
    }

    @GetMapping("/trucks")
    public String showTrucksView(Model model, @AuthenticationPrincipal User authenticatedUser) {
        model.addAttribute("trucks", truckService.getTrucksByDriver(authenticatedUser.getId()));
        return "truck/trucks";
    }

    @GetMapping("/trucks/{truckId}")
    public String showTruckView(Model model, @AuthenticationPrincipal User authenticatedUser,
                                @PathVariable Long truckId, Locale locale) {
        Truck truck = truckService.getTruckOrThrow(truckId);
        if (!authenticatedUser.getId().equals(truck.getDriver().getId())) {
            return "redirect:/error/forbidden";
        }
        if (truck.getStatus() == StatusTruck.INACTIVE) {
            model.addAttribute("warningMessage", message.get("truck.inactive.warning", locale));
        }
        model.addAttribute("truckForm", CreateTruckRequest.of(truck));
        model.addAttribute("fuelTypes", FuelType.getFuelTypesAsNames());
        model.addAttribute("transmissions", Transmission.getTransmissionsAsNames());
        return "truck/truck";
    }

    @PostMapping("/trucks/{truckId}/update-status")
    public String processUpdateStatus(@PathVariable Long truckId, RedirectAttributes redirectAttributes, Locale locale) {
        Truck updatedTruck = truckService.updateStatus(truckId);
        String messageKey = (updatedTruck.getStatus() == StatusTruck.ACTIVE)
                ? "truck.statusActivated.success" : "truck.statusInactivated.success";
        redirectAttributes.addFlashAttribute("successMessage",
                message.get(messageKey, locale, updatedTruck.getPlate()));
        return "redirect:/trucks";
    }

    @GetMapping("/trucks/new")
    public String showNewTruckView(Model model) {
        model.addAttribute("truckForm", CreateTruckRequest.builder().build());
        model.addAttribute("fuelTypes", FuelType.getFuelTypesAsNames());
        model.addAttribute("transmissions", Transmission.getTransmissionsAsNames());
        return "truck/truck-form";
    }

    @PostMapping("/trucks/new")
    public String processNewTruckForm(@AuthenticationPrincipal User authenticatedUser,
                                      @Valid @ModelAttribute("truckForm") CreateTruckRequest request,
                                      BindingResult bindingResult, Model model,
                                      RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("fuelTypes", FuelType.getFuelTypesAsNames());
            model.addAttribute("transmissions", Transmission.getTransmissionsAsNames());
            return "truck/truck-form";
        }
        truckService.registerTruck(authenticatedUser.getId(), request);
        redirectAttributes.addFlashAttribute("successMessage",
                message.get("truck.created.success", locale, request.getPlate()));
        return "redirect:/trucks";
    }

}
