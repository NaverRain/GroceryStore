package com.naverrain.grocery.core.service.user.impl;

import com.naverrain.grocery.core.dto.user.UserDto;
import com.naverrain.grocery.core.mapper.user.UserMapper;
import com.naverrain.grocery.core.service.user.UserService;
import com.naverrain.grocery.persistence.entity.user.Role;
import com.naverrain.grocery.persistence.entity.user.User;
import com.naverrain.grocery.persistence.repository.user.RoleRepository;
import com.naverrain.grocery.persistence.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    @Override
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::toDto);
    }

    @Override
    @Transactional
    public Optional<UserDto> getUserByUsernameOrEmail(String usernameOrEmail) {
        Optional<UserDto> userDto = getUserByEmail(usernameOrEmail);
        if (!userDto.isPresent()) {
            userDto = getUserByUsername(usernameOrEmail);
        }

        userDto.ifPresent(user -> Hibernate.initialize(user.getRoles()));

        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto assignRolesToUser(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Role> roles = roleRepository.findAllById(roleIds);
        user.getRoles().addAll(roles);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto registerUser(UserDto userDto, String defaultRole) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName(defaultRole)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + defaultRole));
        user.setRoles(Collections.singleton(role));

        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        userRepository.save(user);
    }
}