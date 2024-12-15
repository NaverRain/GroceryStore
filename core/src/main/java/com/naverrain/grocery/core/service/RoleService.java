package com.naverrain.grocery.core.service;

import com.naverrain.grocery.core.dto.RoleDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    RoleDto createRoleDto(RoleDto roleDto);

    Optional<RoleDto> getRoleDtoById(Long id);

    Optional<RoleDto> getRoleDtoByName(String name);

    List<RoleDto> getAllRoleDtos();

    void deleteRoleDtoById(Long id);

    RoleDto assignPrivilegesToRoleDto(Long roleId, List<Long> privilegeId);
}
