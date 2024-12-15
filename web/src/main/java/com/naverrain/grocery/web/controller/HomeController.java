package com.naverrain.grocery.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome(Authentication authentication, Model model){
        if (authentication != null && authentication.isAuthenticated()) {
            // Set the current user's username
            model.addAttribute("currentUser", authentication.getName());
        }
        return "homepage";
    }
}
