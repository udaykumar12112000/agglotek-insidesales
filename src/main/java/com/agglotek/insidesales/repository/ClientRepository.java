package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    public List<Client> findByEmployeeId(Integer employeeId);
}
