package com.naverrain.grocery.core.service.user.impl;

import com.naverrain.grocery.core.dto.user.RoleDto;
import com.naverrain.grocery.core.mapper.user.RoleMapper;
import com.naverrain.grocery.core.service.user.RoleService;
import com.naverrain.grocery.persistence.entity.user.Privilege;
import com.naverrain.grocery.persistence.entity.user.Role;
import com.naverrain.grocery.persistence.repository.user.PrivilegeRepository;
import com.naverrain.grocery.persistence.repository.user.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PrivilegeRepository privilegeRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDto createRoleDto(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public Optional<RoleDto> getRoleDtoById(Long id) {
        return roleRepository.findById(id).map(roleMapper::toDto);
    }

    @Override
    public Optional<RoleDto> getRoleDtoByName(String name) {
        return roleRepository.findByName(name).map(roleMapper::toDto);
    }

    @Override
    public List<RoleDto> getAllRoleDtos() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRoleDtoById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDto assignPrivilegesToRoleDto(Long roleId, List<Long> privilegeIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        List<Privilege> privileges = privilegeRepository.findAllById(privilegeIds);
        role.getPrivileges().addAll(privileges);

        return roleMapper.toDto(roleRepository.save(role));
    }
}

