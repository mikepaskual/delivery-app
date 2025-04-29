package com.mikepaskual.delivery.error.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    public ErrorController() {
        super();
    }

    @GetMapping("not-found")
    public String notFound() {
        return "error/404";
    }

    @GetMapping("forbidden")
    public String forbidden() {
        return "error/403";
    }
}
