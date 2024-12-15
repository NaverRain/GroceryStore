package com.naverrain.grocery.core.mapper;

import com.naverrain.grocery.core.dto.PrivilegeDto;
import com.naverrain.grocery.persistence.entity.Privilege;
import org.springframework.stereotype.Component;

@Component
public class PrivilegeMapper {
    public static Privilege toEntity(PrivilegeDto dto) {
        Privilege privilege = new Privilege();
        privilege.setId(dto.getId());
        privilege.setName(dto.getName());
        return privilege;
    }

    public static PrivilegeDto toDto(Privilege privilege) {
        PrivilegeDto dto = new PrivilegeDto();
        dto.setId(privilege.getId());
        dto.setName(privilege.getName());
        return dto;
    }
}
