package com.naverrain.grocery.core.mapper;

import com.naverrain.grocery.core.dto.UserDto;
import com.naverrain.grocery.persistence.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setMoney(dto.getMoney());
        user.setRoles(dto.getRoles().stream().map(RoleMapper::toEntity).collect(Collectors.toSet()));
        user.setEnabled(true);
        return user;
    }

    public static UserDto toDto(User user) {
        if (user == null){
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setMoney(user.getMoney());
        dto.setRoles(user.getRoles().stream().map(RoleMapper::toDto).collect(Collectors.toSet()));
        dto.setEnabled(true);
        return dto;
    }
}
