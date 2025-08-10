package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.Role;

import java.util.List;

public interface IRoleService {
    Role addRole(Role role);
    List<Role> getAllRoles();

    Role editRole(Role roleData);

    boolean deleteRoleById(Integer roleId);
}