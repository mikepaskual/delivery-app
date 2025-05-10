package com.mikepaskual.delivery.customer.controller;

import com.mikepaskual.delivery.customer.dto.PackResume;
import com.mikepaskual.delivery.customer.service.CustomerService;
import com.mikepaskual.delivery.customer.service.PackService;
import com.mikepaskual.delivery.shared.util.MessageUtil;
import com.mikepaskual.delivery.customer.dto.CreatePackRequest;
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
public class PackController {

    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final MessageUtil message;
    @Autowired
    private final PackService packService;

    public PackController(CustomerService customerService, MessageUtil message, PackService packService) {
        this.customerService = customerService;
        this.message = message;
        this.packService = packService;
    }

    @GetMapping("/packs")
    public String showPackagesView(Model model, @AuthenticationPrincipal User authenticatedUser) {
        model.addAttribute("packs", packService.getPacksBySender(authenticatedUser.getId()));
        return "pack/packs";
    }

    @GetMapping("/packs/{packId}")
    public String showPackView(Model model, @PathVariable Long packId, Locale locale) {
        PackResume pack = packService.getPackResume(packId);
        if (pack.isDiscarded()) {
            model.addAttribute("warningMessage", message.get("pack.discarded.warning", locale));
        }
        model.addAttribute("packForm", pack);
        return "pack/pack";
    }

    @PostMapping("/packs/{packId}")
    public String processDiscardedPack(@PathVariable Long packId,
                                       RedirectAttributes redirectAttributes, Locale locale) {
        packService.discardPack(packId);
        redirectAttributes.addFlashAttribute("successMessage",
                message.get("pack.discarded.success", locale));
        return "redirect:/packs";
    }

    @GetMapping("/packs/new")
    public String showNewPackView(Model model, @AuthenticationPrincipal User authenticatedUser) {
        model.addAttribute("packForm", CreatePackRequest.builder().build());
        model.addAttribute("receivers",
                customerService.getReceiversExcludingSender(authenticatedUser.getId()));
        return "pack/pack-form";
    }

    @PostMapping("/packs/new")
    public String processNewPackForm(@AuthenticationPrincipal User authenticatedUser,
                                     @Valid @ModelAttribute("packForm") CreatePackRequest request,
                                     BindingResult bindingResult, Model model,
                                     RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("receivers",
                    customerService.getReceiversExcludingSender(authenticatedUser.getId()));
            return "pack/pack-form";
        }
        packService.registerPack(authenticatedUser.getId(), request);
        redirectAttributes.addFlashAttribute("successMessage",
                message.get("pack.created.success", locale));
        return "redirect:/packs";
    }

}
