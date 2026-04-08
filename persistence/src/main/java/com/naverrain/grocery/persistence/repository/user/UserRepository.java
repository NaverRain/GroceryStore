package com.naverrain.grocery.persistence.repository.user;

import com.naverrain.grocery.persistence.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT DISTINCT u FROM User u "
            + "LEFT JOIN FETCH u.roles r "
            + "LEFT JOIN FETCH r.privileges "
            + "WHERE u.email = :login")
    Optional<User> findByEmailWithRolesAndPrivileges(@Param("login") String login);

    @Query("SELECT DISTINCT u FROM User u "
            + "LEFT JOIN FETCH u.roles r "
            + "LEFT JOIN FETCH r.privileges "
            + "WHERE u.username = :login")
    Optional<User> findByUsernameWithRolesAndPrivileges(@Param("login") String login);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
