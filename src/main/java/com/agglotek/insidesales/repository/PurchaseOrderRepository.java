package com.agglotek.insidesales.repository;

import com.agglotek.insidesales.dao.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    @Query("SELECT COUNT(p) FROM PurchaseOrder p WHERE SUBSTRING(p.poNumber, 2, 2) = :yearPart")
    Long countByYearPart(String yearPart);

    boolean existsByProjectId(Integer projectId);
}
