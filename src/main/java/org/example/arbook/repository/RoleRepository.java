package org.example.arbook.repository;

import org.example.arbook.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    List<Role> findALlByRoleNameIn(List<String> roleUser);
}