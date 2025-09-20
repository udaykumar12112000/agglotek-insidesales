package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("SELECT i FROM Invoice i WHERE (:userId IS NULL OR i.userId = :userId)")
    List<Invoice> findInvoicesByUserId(@Param("userId") Integer userId);
}
