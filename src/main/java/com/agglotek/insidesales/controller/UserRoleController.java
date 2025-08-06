package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.dao.entity.Role;
import com.agglotek.insidesales.service.api.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.agglotek.insidesales.constants.ApiConstants.*;

@RestController
@RequestMapping(ROLES_APIS)
public class UserRoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping(ADD_ROLE)
    public Role addRole(@RequestBody Role roleData) {
        return roleService.addRole(roleData);
    }

    @GetMapping(GET_ALL_ROLES)
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

}
