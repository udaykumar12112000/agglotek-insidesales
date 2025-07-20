package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.agglotek.insidesales.constants.ApiConstants.*;

@RestController
@RequestMapping(USER_APIS)
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(CREATE_USER)
    public User createUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping(GET_USER)
    public List<User> getUsers(@RequestParam(required = false) Integer userId,
                               @RequestParam(required = false) Integer roleId) {
        if (userId != null) {
            return userService.getUserByUserId(userId);
        } else if (roleId != null) {
            return userService.getUsersByRoleId(roleId);
        } else {
            return userService.getAllUsers();
        }
    }

}
