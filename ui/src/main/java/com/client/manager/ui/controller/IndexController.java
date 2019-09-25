package com.client.manager.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {


    public IndexController() {

    }

    @RequestMapping("/")
    public String showIndexPage(final Model model) {
        return "index";
    }

    @RequestMapping("/index")
    public String showIndex(final Model model) {
        return "index";
    }
}
