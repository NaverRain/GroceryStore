package com.naverrain.grocery.core.mapper.user;

import com.naverrain.grocery.core.dto.user.RoleDto;
import com.naverrain.grocery.persistence.entity.user.Role;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    private final PrivilegeMapper privilegeMapper;

    public RoleMapper(PrivilegeMapper privilegeMapper) {
        this.privilegeMapper = privilegeMapper;
    }

    public Role toEntity(RoleDto dto) {
        if (dto == null) return null;
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        if (dto.getPrivileges() == null || dto.getPrivileges().isEmpty()) {
            role.setPrivileges(new HashSet<>());
        } else {
            role.setPrivileges(
                    dto.getPrivileges().stream()
                            .map(privilegeMapper::toEntity)
                            .collect(Collectors.toSet())
            );
        }
        return role;
    }

    public RoleDto toDto(Role entity) {
        if (entity == null) return null;
        RoleDto dto = new RoleDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrivileges(
                entity.getPrivileges() == null ? new HashSet<>()
                        : entity.getPrivileges().stream()
                        .map(privilegeMapper::toDto)
                        .collect(Collectors.toSet())
        );
        return dto;
    }
}
