package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.Users;
import com.agglotek.insidesales.repository.UserRepository;
import com.agglotek.insidesales.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository usersRepository;

    @Override
    public Users addUser(Users user) {
        return usersRepository.save(user);
    }

}