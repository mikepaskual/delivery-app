package com.mikepaskual.delivery.driver.controller;

import com.mikepaskual.delivery.driver.dto.UpdateDriverRequest;
import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.service.DriverService;
import com.mikepaskual.delivery.user.model.User;
import com.mikepaskual.delivery.user.model.UserRole;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    private final MessageSource messageSource;

    public DriverController(DriverService driverService, MessageSource messageSource) {
        this.driverService = driverService;
        this.messageSource = messageSource;
    }

    @GetMapping("/drivers")
    public String showDriverForm(Model model, @AuthenticationPrincipal User userAuthenticated) {
        Driver driver = driverService.findById(userAuthenticated.getId());
        UpdateDriverRequest updateDriverForm = UpdateDriverRequest.builder()
                .setAvailableFrom(driver.getAvailableFrom())
                .setAvailableTo(driver.getAvailableTo())
                .setLicenseNumber(driver.getLicenseNumber()).build();
        model.addAttribute("updateDriverForm", updateDriverForm);
        return "driver/profile";
    }

    @PostMapping("/drivers/submit")
    public String processDriverForm(@Valid @ModelAttribute("updateDriverForm") UpdateDriverRequest request,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal User userAuthenticated,
                                    RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            return "driver/profile";
        }
        driverService.update(userAuthenticated.getId(), request);
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage("driver.updated.success", null, locale));
        return "redirect:/";
    }
}
