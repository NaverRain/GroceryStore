package com.naverrain.grocery.core.service.impl;

import com.naverrain.grocery.core.dto.PrivilegeDto;
import com.naverrain.grocery.core.mapper.PrivilegeMapper;
import com.naverrain.grocery.core.service.PrivilegeService;
import com.naverrain.grocery.persistence.entity.Privilege;
import com.naverrain.grocery.persistence.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository){
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public PrivilegeDto createPrivilege(PrivilegeDto privilegeDto) {
        Privilege privilege = PrivilegeMapper.toEntity(privilegeDto);
        return PrivilegeMapper.toDto(privilegeRepository.save(privilege));
    }

    @Override
    public Optional<PrivilegeDto> getPrivilegeById(Long id) {
        return privilegeRepository.findById(id).map(PrivilegeMapper::toDto);
    }

    @Override
    public List<PrivilegeDto> getAllPrivileges() {
        return privilegeRepository.findAll().stream().map(PrivilegeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deletePrivilegeById(Long id) {
        privilegeRepository.deleteById(id);
    }
}
