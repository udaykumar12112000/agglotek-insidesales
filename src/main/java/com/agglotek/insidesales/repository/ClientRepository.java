package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
     List<Client> findByUserId(Integer userId);

}
