package com.mikepaskual.delivery.driver.controller;

import com.mikepaskual.delivery.driver.dto.UpdateDriverRequest;
import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.service.DriverService;
import com.mikepaskual.delivery.user.model.User;
import com.mikepaskual.delivery.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DriverController {

    @Autowired
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/drivers")
    public String showDriverForm(Model model, @AuthenticationPrincipal User userAuthenticated) {
        if (userAuthenticated.getRoles().stream()
                .noneMatch(role -> UserRole.DRIVER.name().equals(role.getName()))) {
            return "redirect:/error/forbidden";
        }
        Driver driver = driverService.findById(userAuthenticated.getId());
        UpdateDriverRequest updateDriverForm = UpdateDriverRequest.builder()
                .setAvailableFrom(driver.getAvailableFrom())
                .setAvailableTo(driver.getAvailableTo())
                .setLicenseNumber(driver.getLicenseNumber()).build();
        model.addAttribute("updateDriverForm", updateDriverForm);
        return "driver/profile";
    }

    @PostMapping("/drivers/submit")
    public String processDriverForm() {
        return "redirect:/";
    }
}
