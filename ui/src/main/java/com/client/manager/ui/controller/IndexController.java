package com.client.manager.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {


    public IndexController() {

    }

    @GetMapping
    public String showEntryPoint(final Model model) {
        return "index";
    }

    @GetMapping("/index")
    public String showIndex(final Model model) {
        return "index";
    }
}