package com.client.manager.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    public LoginController(
    ) {
    }

    @GetMapping
    public String showLoginPage(final Model model) {
        return "login";
    }
}
