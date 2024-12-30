package com.naverrain.grocery.core.service.user.impl;

import com.naverrain.grocery.core.dto.user.PrivilegeDto;
import com.naverrain.grocery.core.mapper.user.PrivilegeMapper;
import com.naverrain.grocery.core.service.user.PrivilegeService;
import com.naverrain.grocery.persistence.entity.user.Privilege;
import com.naverrain.grocery.persistence.repository.user.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeMapper privilegeMapper;

    @Autowired
    public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository, PrivilegeMapper privilegeMapper) {
        this.privilegeRepository = privilegeRepository;
        this.privilegeMapper = privilegeMapper;
    }

    @Override
    public PrivilegeDto createPrivilege(PrivilegeDto privilegeDto) {
        Privilege privilege = privilegeMapper.toEntity(privilegeDto);
        return privilegeMapper.toDto(privilegeRepository.save(privilege));
    }

    @Override
    public Optional<PrivilegeDto> getPrivilegeById(Long id) {
        return privilegeRepository.findById(id).map(privilegeMapper::toDto);
    }

    @Override
    public List<PrivilegeDto> getAllPrivileges() {
        return privilegeRepository.findAll()
                .stream()
                .map(privilegeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePrivilegeById(Long id) {
        privilegeRepository.deleteById(id);
    }
}
