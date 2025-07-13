package com.agglotek.insidesales.impl;

import com.agglotek.insidesales.model.Users;
import com.agglotek.insidesales.repository.UserRepository;
import com.agglotek.insidesales.service.IUserService;
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