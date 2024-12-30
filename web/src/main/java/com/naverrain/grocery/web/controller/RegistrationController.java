package com.naverrain.grocery.web.controller;


import com.naverrain.grocery.core.dto.user.UserDto;
import com.naverrain.grocery.core.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Value("${default.user.role:ROLE_CUSTOMER}")
    private String defaultRole;

    @Autowired
    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                               BindingResult bindingResult,
                               Model model) {
        if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
            bindingResult.rejectValue("repeatPassword", "error.userDto", "Passwords do not match!");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            userService.registerUser(userDto, defaultRole);
        } catch (IllegalArgumentException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "registration";
        }

        return "redirect:/login";
    }

}
