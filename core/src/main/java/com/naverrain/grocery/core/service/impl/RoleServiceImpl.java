package com.naverrain.grocery.core.service.impl;

import com.naverrain.grocery.core.dto.RoleDto;
import com.naverrain.grocery.core.mapper.RoleMapper;
import com.naverrain.grocery.core.service.RoleService;
import com.naverrain.grocery.persistence.entity.Privilege;
import com.naverrain.grocery.persistence.entity.Role;
import com.naverrain.grocery.persistence.repository.PrivilegeRepository;
import com.naverrain.grocery.persistence.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public RoleDto createRoleDto(RoleDto roleDto) {
        Role role = RoleMapper.toEntity(roleDto);
        return RoleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public Optional<RoleDto> getRoleDtoById(Long id) {
        return roleRepository.findById(id).map(RoleMapper::toDto);
    }

    @Override
    public Optional<RoleDto> getRoleDtoByName(String name) {
        return roleRepository.findByName(name).map(RoleMapper::toDto);
    }

    @Override
    public List<RoleDto> getAllRoleDtos() {
        return roleRepository.findAll().stream().map(RoleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteRoleDtoById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDto assignPrivilegesToRoleDto(Long roleId, List<Long> privilegeId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        List<Privilege> privileges = privilegeRepository.findAllById(privilegeId);
        role.getPrivileges().addAll(privileges);

        return RoleMapper.toDto(roleRepository.save(role));
    }
}
