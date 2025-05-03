package com.mikepaskual.delivery.driver.controller;

import com.mikepaskual.delivery.driver.dto.UpdateDriverRequest;
import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.service.DriverService;
import com.mikepaskual.delivery.shared.util.MessageUtil;
import com.mikepaskual.delivery.user.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class DriverController {

    @Autowired
    private final DriverService driverService;
    @Autowired
    private final MessageUtil messageUtil;

    public DriverController(DriverService driverService, MessageUtil messageUtil) {
        this.driverService = driverService;
        this.messageUtil = messageUtil;
    }

    @GetMapping("/driver-settings")
    public String showDriverView(Model model, @AuthenticationPrincipal User authenticatedUser) {
        Driver driver = driverService.getDriverOrThrow(authenticatedUser.getId());
        model.addAttribute("driverForm", UpdateDriverRequest.builder()
                .setAvailableFrom(driver.getAvailableFrom())
                .setAvailableTo(driver.getAvailableTo())
                .setLicenseNumber(driver.getLicenseNumber()).build());
        return "driver/driver-settings";
    }

    @PostMapping("/driver-settings")
    public String processDriverForm(@Valid @ModelAttribute("driverForm") UpdateDriverRequest request,
                                    BindingResult bindingResult, @AuthenticationPrincipal User authenticatedUser,
                                    RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            return "driver/driver-settings";
        }
        driverService.update(authenticatedUser.getId(), request);
        redirectAttributes.addFlashAttribute("successMessage",
                messageUtil.get("driver.updated.success", locale));
        return "redirect:/";
    }
}
