package com.naverrain.grocery.persistence.repository.user;

import com.naverrain.grocery.persistence.entity.user.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    List<Privilege> findByNameIn(List<String> names);

}
