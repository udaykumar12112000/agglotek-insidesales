package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUserId(Integer userId);

    List<User> findByRoleId(Integer roleId);

    User findByEmail(String email);

    @Query(value = "SELECT COUNT(*) " +
            "FROM quotations " +
            "WHERE user_id = :userId " +
            "AND date_of_quotation >= date_trunc('month', CURRENT_DATE) " +
            "AND date_of_quotation < (date_trunc('month', CURRENT_DATE) + INTERVAL '1 month')",
            nativeQuery = true)
    int getTotalNumberOfBidsRecievedThisMonth(@Param("userId")Integer userId);


    @Query(value = "SELECT COUNT(*) " +
            "FROM quotations " +
            "WHERE user_id = :userId " +
            "AND date_of_quotation >= date_trunc('month', CURRENT_DATE) " +
            "AND date_of_quotation < (date_trunc('month', CURRENT_DATE) + INTERVAL '1 month') " +
            "AND ( " +
            "  (:status IS NULL OR :status = '' AND (quotation_status IS NULL OR quotation_status = '')) " +
            "  OR (:status IS NOT NULL AND :status <> '' AND quotation_status = :status) " +
            ")",
            nativeQuery = true)
    int getTotalNumberOfBidsYetToUpdateThisMonth(@Param("userId")Integer userId, @Param("status")String status);

    @Query(value = "SELECT COUNT(*) " +
            "FROM quotations " +
            "WHERE user_id = :userId " +
            "AND date_of_quotation >= date_trunc('year', CURRENT_DATE) " +
            "AND date_of_quotation < (date_trunc('year', CURRENT_DATE) + INTERVAL '1 year')",
            nativeQuery = true)
    int getTotalNumberOfBidsRecievedThisYear(@Param("userId")Integer userId);

    @Query(value = "SELECT COUNT(DISTINCT c.client_id) " +
            "FROM clients c " +
            "JOIN quotations q ON c.client_id = q.client_id " +
            "WHERE c.user_id = :userId " +
            "AND date_trunc('year', c.insert_time) = date_trunc('year', CURRENT_DATE) " +
            "AND q.quotation_status = 'Alloted'",
            nativeQuery = true)
    int getTotalNewClientsAchievedThisYear(@Param("userId")Integer userId);

    @Query(value = "SELECT COUNT(*) " +
            "FROM quotations " +
            "WHERE user_id = :userId " +
            "AND date_of_quotation >= date_trunc('year', CURRENT_DATE) " +
            "AND date_of_quotation < (date_trunc('year', CURRENT_DATE) + INTERVAL '1 year') " +
            "AND ( " +
            "  (:allotted IS NULL OR :allotted = '' AND (quotation_status IS NULL OR quotation_status = '')) " +
            "  OR (:allotted IS NOT NULL AND :allotted <> '' AND quotation_status = :allotted) " +
            ")",
            nativeQuery = true)
    int getTotalNumberOfProjectsAllotedThisYear(@Param("userId")Integer userId, @Param("allotted")String allotted);

    @Query(value = "SELECT COUNT(*) " +
            "FROM quotations " +
            "WHERE user_id = :userId " +
            "AND date_of_quotation >= date_trunc('month', CURRENT_DATE) " +
            "AND date_of_quotation < (date_trunc('month', CURRENT_DATE) + INTERVAL '1 month') " +
            "AND ( " +
            "  (:allotted IS NULL OR :allotted = '' AND (quotation_status IS NULL OR quotation_status = '')) " +
            "  OR (:allotted IS NOT NULL AND :allotted <> '' AND quotation_status = :allotted) " +
            ")",
            nativeQuery = true)
    int getTotalNumberOfProjectsAllotedThisMonth(Integer userId, String allotted);
}
