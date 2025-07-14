package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.Role;

import java.util.List;

public interface IRoleService {
    public Role addRole(Role role);
    public List<Role> getAllRoles();

}