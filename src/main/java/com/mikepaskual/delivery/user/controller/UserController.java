package com.mikepaskual.delivery.user.controller;

import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.model.User;
import com.mikepaskual.delivery.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new CreateUserRequest());
        return "register";
    }

    @PostMapping("/auth/register/submit")
    public String processRegisterForm(@ModelAttribute("user") CreateUserRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "register";
        User saved = userService.registerUser(request);
        return "redirect:/login";
    }
}
