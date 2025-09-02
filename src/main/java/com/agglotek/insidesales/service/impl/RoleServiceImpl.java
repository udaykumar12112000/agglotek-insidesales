package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.Role;
import com.agglotek.insidesales.repository.RoleRepository;
import com.agglotek.insidesales.service.api.IRoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Role editRole(Role roleData) {
        Role existingRole = roleRepository.findById(roleData.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + roleData.getRoleId()));

        existingRole.setRoleName(roleData.getRoleName());
        return roleRepository.save(existingRole);
    }

    public boolean deleteRoleById(Integer roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            roleRepository.deleteById(roleId);
            return true;
        }
        return false;
    }

    @Override
    public String getRoleNameById(Integer roleId) {
        return roleRepository.findRoleNameByRoleId(roleId);
    }

    @Override
    public Integer getRoleIdByRoleName(String roleName) {
        return roleRepository.findRoleIdByRoleName(roleName);
    }
}