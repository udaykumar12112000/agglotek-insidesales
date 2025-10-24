package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

    public interface ClientRepository extends JpaRepository<Client, Integer> {
         List<Client> findByUserId(Integer userId);

        @Query("SELECT c FROM Client c " +
                "JOIN Quotation q ON c.clientId = q.clientId " +
                "JOIN Project p ON q.quotationId = p.quotationId " +
                "WHERE p.projectId = :projectId")
        Optional<Client> findClientByProjectId(@Param("projectId") Integer projectId);

        Client getByClientId(Integer clientId);
    }
