package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.dao.entity.Users;
import com.agglotek.insidesales.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.agglotek.insidesales.constants.ApiConstants.CREATE_USER;
import static com.agglotek.insidesales.constants.ApiConstants.USER_APIS;

@RestController
@RequestMapping(USER_APIS)
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(CREATE_USER)
    public Users createUser(@RequestBody Users user) {
        return userService.addUser(user);
    }
}
