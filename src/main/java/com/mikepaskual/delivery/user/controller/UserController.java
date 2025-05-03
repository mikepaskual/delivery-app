package com.mikepaskual.delivery.user.controller;

import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.dto.UpdatePasswordRequest;
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

import java.util.Locale;

@Controller
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final MessageSource messageSource;

    public UserController(UserService userService, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHome() {
        return "user/home";
    }

    @GetMapping("/change-password")
    public String showChangePasswordView(Model model) {
        model.addAttribute("changePassForm", UpdatePasswordRequest.builder().build());
        return "user/change-password";
    }

    @PostMapping("/change-password")
    public String processChangePassForm(@Valid @ModelAttribute("changePassForm") UpdatePasswordRequest request,
                                        BindingResult bindingResult, @AuthenticationPrincipal User userAuthenticated,
                                        RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            return "user/change-password";
        }
        boolean wrongPass = !userService.isCurrentPassword(userAuthenticated.getId(), request.getCurrentPassword());
        if (wrongPass) {
            bindingResult.rejectValue("currentPassword", "password.validation.currentPassword.error");
            return "user/change-password";
        }
        userService.updatePassword(userAuthenticated.getId(), request.getNewPassword());
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage("password.updated.success", null, locale));
        return "redirect:/";
    }

    @GetMapping("/auth/register")
    public String showRegisterView(Model model) {
        model.addAttribute("registerForm", CreateUserRequest.builder().build());
        model.addAttribute("roles", UserRole.getPublicRolesAsNames());
        return "register";
    }

    @PostMapping("/auth/register")
    public String processRegisterForm(@Valid @ModelAttribute("registerForm") CreateUserRequest request,
                                      BindingResult bindingResult, Model model,
                                      RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", UserRole.getPublicRolesAsNames());
            return "register";
        }
        userService.registerUser(request);
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage("register.success", null, locale));
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String showProfileView(Model model, @AuthenticationPrincipal User userAuthenticated) {
        User user = userService.getUserOrThrow(userAuthenticated.getId());
        UpdateUserRequest updateUserForm = UpdateUserRequest.builder()
                .setBirthday(user.getBirthday())
                .setFirstName(user.getFirstName())
                .setGender(user.getGender() == null ? null : user.getGender().name())
                .setLastName(user.getLastName())
                .setPhone(user.getPhone()).build();
        model.addAttribute("profileForm", updateUserForm);
        model.addAttribute("genders", Gender.getGendersAsNames());
        return "user/profile";
    }

    @PostMapping("/profile")
    public String processProfileForm(@Valid @ModelAttribute("profileForm") UpdateUserRequest request,
                                     BindingResult bindingResult, Model model,
                                     @AuthenticationPrincipal User userAuthenticaded,
                                     RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.getGendersAsNames());
            return "user/profile";
        }
        userService.updateUser(userAuthenticaded.getId(), request);
        redirectAttributes.addFlashAttribute("successMessage", messageSource.getMessage("profile.updated.success", null, locale));
        return "redirect:/";
    }

}
