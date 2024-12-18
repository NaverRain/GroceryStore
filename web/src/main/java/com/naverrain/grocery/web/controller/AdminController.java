package com.naverrain.grocery.web.controller;

import com.naverrain.grocery.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String getAdmin(){
        return "admin";
    }

    @PostMapping("/admin/users/{id}/toggle")
    public void toggleUser(@PathVariable Long id){

    }
}
