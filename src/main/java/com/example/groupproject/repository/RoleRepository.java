package com.example.groupproject.repository;

import com.example.groupproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsById(long roleId);

    //Spring Security setup
    Optional<Role> findByAuthority(String authority);

}
