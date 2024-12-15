package com.naverrain.grocery.web.handler;

import com.naverrain.grocery.core.dto.PrivilegeDto;
import com.naverrain.grocery.core.dto.UserDto;
import com.naverrain.grocery.core.service.UserService;
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

        System.out.println("Encoded password from DB: " + userDto.getPassword());

        return User
                .builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .authorities(userDto.getRoles().stream()
                        .flatMap(role -> role.getPrivileges().stream())
                        .map(PrivilegeDto::getName)
                        .toArray(String[]::new))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!userDto.getEnabled())
                .build();
    }
}
