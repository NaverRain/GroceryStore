package com.naverrain.grocery.core.service.user;

import com.naverrain.grocery.core.dto.user.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> getUserById(Long id);

    Optional<UserDto> getUserByUsername(String username);

    Optional<UserDto> getUserByEmail(String email);

    Optional<UserDto> getUserByUsernameOrEmail(String usernameOrEmail);

    List<UserDto> getAllUsers();

    void deleteUserById(Long id);

    UserDto assignRolesToUser(Long userId, List<Long> roleIds);

    UserDto registerUser(UserDto user, String defaultRole);

    void updateUser(UserDto userDto);
}
