package com.naverrain.grocery.core.mapper.user;

import com.naverrain.grocery.core.dto.user.PrivilegeDto;
import com.naverrain.grocery.persistence.entity.user.Privilege;
import org.springframework.stereotype.Component;

@Component
public class PrivilegeMapper {
    public Privilege toEntity(PrivilegeDto dto) {
        if (dto == null) return null;
        Privilege privilege = new Privilege();
        privilege.setId(dto.getId());
        privilege.setName(dto.getName());
        return privilege;
    }

    public PrivilegeDto toDto(Privilege entity) {
        if (entity == null) return null;
        PrivilegeDto dto = new PrivilegeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
