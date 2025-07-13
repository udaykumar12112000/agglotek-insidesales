package com.agglotek.insidesales.service;

import com.agglotek.insidesales.model.Role;

import java.util.List;

public interface IRoleService {
    public Role addRole(Role role);
    public List<Role> getAllRoles();

}