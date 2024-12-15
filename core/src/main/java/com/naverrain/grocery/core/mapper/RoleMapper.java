package com.naverrain.grocery.core.mapper;

import com.naverrain.grocery.core.dto.RoleDto;
import com.naverrain.grocery.persistence.entity.Role;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public static Role toEntity(RoleDto dto) {
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        role.setPrivileges(dto.getPrivileges().stream().map(PrivilegeMapper::toEntity).collect(Collectors.toSet()));
        return role;
    }

    public static RoleDto toDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setPrivileges(role.getPrivileges().stream().map(PrivilegeMapper::toDto).collect(Collectors.toSet()));
        return dto;
    }
}
