package com.example.demo.services.implementations;

import com.example.demo.models.role.Role;
import com.example.demo.repositories.contracts.RoleRepository;
import com.example.demo.services.contracts.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.createRole(role);
    }
}
