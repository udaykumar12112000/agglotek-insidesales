package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.model.Role;
import com.agglotek.insidesales.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class UserRoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/addRole")
    public Role addRole(@RequestParam String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        return roleService.addRole(role);
    }

    @GetMapping("/getAllRoles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }


}