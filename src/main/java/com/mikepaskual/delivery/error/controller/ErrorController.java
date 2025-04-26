package com.mikepaskual.delivery.error.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    public ErrorController() {
        super();
    }

    @GetMapping("/forbidden")
    public String forbidden() {
        return "error/forbidden";
    }
}
