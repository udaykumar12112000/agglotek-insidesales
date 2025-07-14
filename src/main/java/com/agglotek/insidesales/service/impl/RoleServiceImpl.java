package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.Role;
import com.agglotek.insidesales.repository.RoleRepository;
import com.agglotek.insidesales.service.api.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}