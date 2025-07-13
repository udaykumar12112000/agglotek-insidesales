package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.model.Users;
import com.agglotek.insidesales.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/createUser")
    public Users createUser(@RequestBody Users user) {
        return userService.addUser(user);
    }
}
