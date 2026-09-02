package com.bookseat.authentication.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HomeController {
    @GetMapping("/home")
    public String home() {
        return "This is home page!";
    }

    @GetMapping("/services")
    public String services() {
        return "Here are some protected services!";
    }
}

