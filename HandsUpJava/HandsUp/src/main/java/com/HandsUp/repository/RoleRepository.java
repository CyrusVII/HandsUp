package com.HandsUp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.HandsUp.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
