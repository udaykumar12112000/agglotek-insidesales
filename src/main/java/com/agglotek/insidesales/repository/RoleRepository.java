package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r.roleName FROM Role r WHERE r.roleId = :roleId")
    String findRoleNameByRoleId(@Param("roleId") Integer roleId);
}