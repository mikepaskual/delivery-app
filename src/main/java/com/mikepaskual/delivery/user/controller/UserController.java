package com.mikepaskual.delivery.user.controller;

import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.dto.UpdateUserRequest;
import com.mikepaskual.delivery.user.model.Gender;
import com.mikepaskual.delivery.user.model.User;
import com.mikepaskual.delivery.user.model.UserRole;
import com.mikepaskual.delivery.user.service.UserService;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private MessageSource messageSource;

    public UserController(UserService userService, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHome() {
        return "user/home";
    }

    @GetMapping("/auth/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", CreateUserRequest.builder()
                .setCreatedAt(LocalDateTime.now()).build());
        model.addAttribute("roles", UserRole.getPublicRolesAsNames());
        return "register";
    }

    @PostMapping("/auth/register/submit")
    public String processRegisterForm(@ModelAttribute("user") CreateUserRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.registerUser(request);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String showProfileForm(Model model, @AuthenticationPrincipal User userAuthentication) {
        User user = userService.findUser(userAuthentication.getId());
        UpdateUserRequest updateUserForm = UpdateUserRequest.builder()
                .setBirthday(user.getBirthday())
                .setFirstName(user.getFirstName())
                .setGender(user.getGender() == null ? null : user.getGender().name())
                .setLastName(user.getLastName())
                .setPhone(user.getPhone()).build();
        model.addAttribute("updateUserForm", updateUserForm);
        model.addAttribute("genders", Arrays.stream(Gender.values()).map(Enum::name).collect(Collectors.toSet()));
        return "user/profile";
    }

    @PostMapping("/profile/submit")
    public String processProfileForm(@Valid @ModelAttribute("updateUserForm") UpdateUserRequest request,
                                     BindingResult bindingResult, Model model,
                                     @AuthenticationPrincipal User user,
                                     RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Arrays.stream(Gender.values()).map(Enum::name).collect(Collectors.toSet()));
            return "user/profile";
        }
        userService.updateUser(user.getId(), request);
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage("profile.updated.success", null, locale));
        return "redirect:/";
    }

}
