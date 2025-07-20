package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Integer> {
    boolean existsByClientId(Integer clientId);

    @Query("SELECT q.quotationNumber FROM Quotation q WHERE q.quotationNumber LIKE :prefix ORDER BY q.quotationNumber DESC LIMIT 1")
    String findLastQuotationNumber(@Param("prefix") String prefix);
}

