package com.HandsUp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HandsUp.entities.Role;
import com.HandsUp.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_COMPANY");
        createRoleIfNotFound("ROLE_ADMIN");
    }

    private void createRoleIfNotFound(String name) {
        if (roleRepository.findByName(name) == null) {
            Role newRole = new Role();
            newRole.setName(name);
            roleRepository.save(newRole);
        }
    }
}
