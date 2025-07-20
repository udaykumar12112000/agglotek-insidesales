package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.repository.UserRepository;
import com.agglotek.insidesales.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository usersRepository;

    @Override
    public User addUser(User user) {
        return usersRepository.save(user);
    }

    @Override
    public List<User> getUserByUserId(Integer userId) {
        return usersRepository.findByUserId(userId);
    }

    @Override
    public List<User> getUsersByRoleId(Integer roleId) {
        return usersRepository.findByRoleId(roleId);
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User findByEmail(String email){
        return usersRepository.findByEmail(email);
    }

}
