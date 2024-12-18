package com.naverrain.grocery.web.controller;

import com.naverrain.grocery.core.dto.UserDto;
import com.naverrain.grocery.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getProfilePage(Model model, Principal principal) {
        UserDto userDto;

        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) principal;
            Object oauth2User = authToken.getPrincipal();

            if (oauth2User instanceof OidcUser oidcUser) {
                userDto = mapGoogleUserToUserDto(oidcUser);
            } else if (oauth2User instanceof DefaultOAuth2User defaultOAuth2User) {
                userDto = mapDefaultOAuth2UserToUserDto(defaultOAuth2User);
            } else {
                throw new IllegalArgumentException("Unsupported OAuth2User type: " + oauth2User.getClass());
            }
        } else {
            String username = principal.getName();
            userDto = userService.getUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with this username: " + username));
        }

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

    private UserDto mapGoogleUserToUserDto(OidcUser oidcUser) {
        UserDto userDto = new UserDto();
        userDto.setFirstName((String) oidcUser.getAttributes().get("given_name"));
        userDto.setLastName((String) oidcUser.getAttributes().get("family_name"));
        userDto.setEmail((String) oidcUser.getAttributes().get("email"));
        userDto.setUsername((String) oidcUser.getAttributes().get("email"));
        return userDto;
    }

    private UserDto mapDefaultOAuth2UserToUserDto(DefaultOAuth2User oauth2User) {
        UserDto userDto = new UserDto();
        userDto.setFirstName((String) oauth2User.getAttributes().getOrDefault("given_name", "N/A"));
        userDto.setLastName((String) oauth2User.getAttributes().getOrDefault("family_name", "N/A"));
        userDto.setEmail((String) oauth2User.getAttributes().getOrDefault("email", "N/A"));
        userDto.setUsername((String) oauth2User.getAttributes().getOrDefault("email", "N/A"));
        return userDto;
    }


}
