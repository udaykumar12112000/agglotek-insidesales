package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUserId(Integer userId);

    List<User> findByRoleId(Integer roleId);
}
