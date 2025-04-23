package com.mikepaskual.delivery.user.controller;

import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.dto.UpdateUserRequest;
import com.mikepaskual.delivery.user.model.Gender;
import com.mikepaskual.delivery.user.model.User;
import com.mikepaskual.delivery.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", CreateUserRequest.builder().build());
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
    public String showProfileForm(Model model, @AuthenticationPrincipal User user) {
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
}
