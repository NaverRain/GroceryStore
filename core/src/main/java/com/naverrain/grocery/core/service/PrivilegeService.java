package com.naverrain.grocery.core.service;

import com.naverrain.grocery.core.dto.PrivilegeDto;

import java.util.List;
import java.util.Optional;

public interface PrivilegeService {

    PrivilegeDto createPrivilege(PrivilegeDto privilegeDto);

    Optional<PrivilegeDto> getPrivilegeById(Long id);

    List<PrivilegeDto> getAllPrivileges();

    void deletePrivilegeById(Long id);
}
