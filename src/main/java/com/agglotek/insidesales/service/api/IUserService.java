package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.User;

import java.util.List;

public interface IUserService {
    User addUser(User user);
    List<User> getUserByUserId(Integer userId);
    List<User> getUsersByRoleId(Integer roleId);
    List<User> getAllUsers();
}