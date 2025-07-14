package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {
}