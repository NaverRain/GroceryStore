package com.naverrain.grocery.web.service;

import com.naverrain.grocery.core.dto.user.PrivilegeDto;
import com.naverrain.grocery.core.dto.user.UserDto;
import com.naverrain.grocery.core.service.user.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public DefaultUserDetailsService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserDto userDto = userService.getUserByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        System.out.println("User fetched: " + userDto);
        System.out.println("Encoded password: " + userDto.getPassword());

        if (userDto.getPassword() == null) {
            throw new IllegalStateException("Password is null for user: " + usernameOrEmail);
        }


        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .authorities(userDto.getRoles().stream()
                        .flatMap(role -> role.getPrivileges().stream())
                        .map(PrivilegeDto::getName)
                        .toArray(String[]::new))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!userDto.isEnabled())
                .build();
    }

}
