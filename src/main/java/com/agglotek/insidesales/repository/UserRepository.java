package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.dto.UserWithRoleDto;
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
    Integer getTotalNumberOfBidsRecievedThisMonth(@Param("userId")Integer userId);


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
    Integer getTotalNumberOfBidsYetToUpdateThisMonth(@Param("userId")Integer userId, @Param("status")String status);

    @Query(value = "SELECT COUNT(*) " +
            "FROM quotations " +
            "WHERE user_id = :userId " +
            "AND date_of_quotation >= date_trunc('year', CURRENT_DATE) " +
            "AND date_of_quotation < (date_trunc('year', CURRENT_DATE) + INTERVAL '1 year')",
            nativeQuery = true)
    Integer getTotalNumberOfBidsRecievedThisYear(@Param("userId")Integer userId);

    @Query(value = "SELECT COUNT(DISTINCT c.client_id) " +
            "FROM clients c " +
            "JOIN quotations q ON c.client_id = q.client_id " +
            "WHERE c.user_id = :userId " +
            "AND date_trunc('year', c.insert_time) = date_trunc('year', CURRENT_DATE) " +
            "AND q.quotation_status = 'Alloted'",
            nativeQuery = true)
    Integer getTotalNewClientsAchievedThisYear(@Param("userId")Integer userId);

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
    Integer getTotalNumberOfProjectsAllotedThisYear(@Param("userId")Integer userId, @Param("allotted")String allotted);

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
    Integer getTotalNumberOfProjectsAllotedThisMonth(Integer userId, String allotted);
    List<User> findBySupUserId(Integer supUserId);

    @Query(value = "SELECT COUNT(*) > 0 FROM users WHERE user_id = :userId AND role_id = 1", nativeQuery = true)
    boolean isAdminUser(@Param("userId") Integer userId);

    @Query(
            value = "SELECT u.email " +
                    "FROM users u " +
                    "JOIN roles r ON u.role_id = r.role_id " +
                    "WHERE r.role_name = 'ACCOUNTS'",
            nativeQuery = true
    )
    List<String> getAcctMail();

    @Query(
            value = "SELECT u.user_id AS userId, " +
                    "u.agglo_user_id AS aggloUserId, " +
                    "u.name, " +
                    "u.designation, " +
                    "u.department, " +
                    "u.email, " +
                    "u.phone_number AS phoneNumber, " +
                    "u.role_id AS roleId, " +
                    "r.role_name AS roleName, " +
                    "u.sup_user_id AS supUserId " +
                    "FROM users u " +
                    "JOIN roles r ON u.role_id = r.role_id",
            nativeQuery = true)
    List<UserWithRoleDto> findAllUsersWithRole();
}
