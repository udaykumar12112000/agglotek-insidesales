package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Integer> {
    boolean existsByClientId(Integer clientId);

    @Query("SELECT q.quotationNumber FROM Quotation q WHERE q.quotationNumber LIKE :prefix ORDER BY q.quotationNumber DESC LIMIT 1")
    String findLastQuotationNumber(@Param("prefix") String prefix);

    @Query(value = "SELECT * FROM quotations " +
            "WHERE user_id = :userId " +
            "AND quotation_due_date BETWEEN CURRENT_DATE - INTERVAL '7 days' AND CURRENT_DATE + INTERVAL '7 days'",
            nativeQuery = true)
    List<Quotation> getAllNotificationsToTheUser(@Param("userId")Integer userId);

    @Transactional
    @Modifying
    @Query("UPDATE Quotation q SET q.quotationStatus = :status WHERE q.quotationId = :quotationId")
    void updateQuotationStatus(@Param("status")String status, @Param("quotationId")Integer quotationId);
  
    List<Quotation> findByUserId(Integer userId);

    List<Quotation> findByUserIdAndQuotationStatusIsNull(Integer userId);

    List<Quotation> findByUserIdAndQuotationStatus(Integer userId, String quotationStatus);

}

