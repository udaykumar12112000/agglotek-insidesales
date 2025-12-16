package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

        @Transactional
        @Modifying
        @Query("UPDATE Client c SET c.assignedSalesUserId = :userId WHERE c.clientId IN :clientIds")
        int assignClientsToUser(Integer userId, List<Integer> clientIds);

        // Case 2: get clients where assigned_sales_user_id is null
        List<Client> findByAssignedSalesUserIdIsNull();

        // Case 3: get clients assigned to a user
        List<Client> findByAssignedSalesUserId(Integer userId);

        @Modifying
        @Transactional
        @Query("UPDATE Client c SET c.isFabricator = TRUE WHERE c.clientId = :clientId")
        int markAsFabricator(@Param("clientId") Integer clientId);

        List<Client> findByIsFabricatorTrue();

        List<Client> findByAssignedSalesUserIdAndIsFabricatorTrue(Integer userId);

    }
