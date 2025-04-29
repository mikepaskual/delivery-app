package com.mikepaskual.delivery.truck.controller;

import com.mikepaskual.delivery.driver.service.DriverService;
import com.mikepaskual.delivery.truck.dto.CreateTruckRequest;
import com.mikepaskual.delivery.truck.model.FuelType;
import com.mikepaskual.delivery.truck.model.StatusTruck;
import com.mikepaskual.delivery.truck.model.Transmission;
import com.mikepaskual.delivery.truck.model.Truck;
import com.mikepaskual.delivery.truck.service.TruckService;
import com.mikepaskual.delivery.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Locale;

@Controller
public class TruckController {

    @Autowired
    private final DriverService driverService;
    @Autowired
    private final MessageSource messageSource;
    @Autowired
    private final TruckService truckService;

    public TruckController(DriverService driverService, MessageSource messageResource, TruckService truckService) {
        this.driverService = driverService;
        this.messageSource = messageResource;
        this.truckService = truckService;
    }

    @GetMapping("/trucks")
    public String showTrucks(Model model, @AuthenticationPrincipal User userAuthenticated) {
        model.addAttribute("trucks", truckService.findTrucks(userAuthenticated.getId()));
        return "truck/trucks";
    }

    @GetMapping("/trucks/{id}")
    public String showTruck(Model model, @AuthenticationPrincipal User userAuthenticated, @PathVariable Long id) {
        Truck truck = truckService.findBy(id);
        if (!userAuthenticated.getId().equals(truck.getDriver().getId())) {
            return "redirect:/error/forbidden";
        }
        model.addAttribute("truckForm", CreateTruckRequest.of(truck));
        model.addAttribute("fuelTypes", FuelType.getFuelTypesAsNames());
        model.addAttribute("status", StatusTruck.getStatusAsNames());
        model.addAttribute("transmissions", Transmission.getTransmissionsAsNames());
        return "truck/truck";
    }

    @PostMapping("/trucks/{id}/update-status")
    public String updateStatus(@AuthenticationPrincipal User userAuthenticated, @PathVariable Long id,
                               RedirectAttributes redirectAttributes, Locale locale) {
        Truck truck = truckService.findBy(id);
        if (!userAuthenticated.getId().equals(truck.getDriver().getId())) {
            return "redirect:/error/forbidden";
        }
        StatusTruck newStatus = (truck.getStatus() == StatusTruck.ACTIVE) ? StatusTruck.INACTIVE : StatusTruck.ACTIVE;
        truck.setStatus(newStatus);
        truckService.registerTruck(CreateTruckRequest.of(truck));
        String messageKey = (newStatus == StatusTruck.ACTIVE) ? "truck.statusActivated.success" : "truck.statusInactivated.success";
        redirectAttributes.addFlashAttribute("successMessage",
                messageSource.getMessage(messageKey, new Object[]{truck.getPlate()}, locale));
        return "redirect:/trucks";
    }

    @GetMapping("/trucks/new")
    public String showNewTruckForm(Model model, @AuthenticationPrincipal User userAuthenticated) {
        CreateTruckRequest truckForm = CreateTruckRequest.builder()
                .setDriver(driverService.findById(userAuthenticated.getId())).build();
        model.addAttribute("truckForm", truckForm);
        model.addAttribute("fuelTypes", FuelType.getFuelTypesAsNames());
        model.addAttribute("transmissions", Transmission.getTransmissionsAsNames());
        return "truck/truck-form";
    }

    @PostMapping("/trucks/new/submit")
    public String processNewTruck(@AuthenticationPrincipal User userAuthenticated,
                                  @ModelAttribute("user") CreateTruckRequest request,
                                  BindingResult bindingResult, Model model,
                                  RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            return "truck/truck-form";
        }
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus(StatusTruck.INACTIVE.name());
        truckService.registerTruck(request);
        redirectAttributes.addFlashAttribute("successMessage",
                messageSource.getMessage("truck.created.success", new Object[]{request.getPlate()}, locale));
        return "redirect:/trucks";
    }

}
