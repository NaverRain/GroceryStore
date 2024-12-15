package com.naverrain.grocery.web.controller;

import com.naverrain.grocery.core.dto.UserDto;
import com.naverrain.grocery.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getProfilePage(Model model, Principal principal) {
        String username = principal.getName();
        UserDto userDto = userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        model.addAttribute("user", userDto);
        return "profile";
    }

    @GetMapping("/edit")
    public String getEditProfilePage(Model model, Principal principal) {
        String username = principal.getName();
        UserDto userDto = userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        model.addAttribute("user", userDto);
        return "edit-profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-profile";
        }
        userService.updateUser(userDto);
        return "redirect:/profile";
    }


}
