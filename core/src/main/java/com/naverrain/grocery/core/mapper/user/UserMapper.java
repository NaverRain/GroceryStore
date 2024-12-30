package com.naverrain.grocery.core.mapper.user;

import com.naverrain.grocery.core.dto.user.UserDto;
import com.naverrain.grocery.persistence.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public User toEntity(UserDto dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setMoney(dto.getMoney());
        user.setPassword(dto.getPassword());
        user.setRoles(
                dto.getRoles().stream()
                        .map(roleMapper::toEntity)
                        .collect(Collectors.toSet())
        );
        user.setEnabled(dto.isEnabled());
        return user;
    }

    public UserDto toDto(User entity) {
        if (entity == null) return null;
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setMoney(entity.getMoney());
        dto.setPassword(entity.getPassword());
        dto.setRoles(
                entity.getRoles().stream()
                        .map(roleMapper::toDto)
                        .collect(Collectors.toSet())
        );
        dto.setEnabled(entity.isEnabled());
        return dto;
    }
}

