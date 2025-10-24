package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.PurchaseOrder;
import java.util.List;
import java.util.Optional;

public interface IPurchaseOrderService {

    PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder);

    List<PurchaseOrder> getAllPurchaseOrders();

    Optional<PurchaseOrder> getPurchaseOrderById(Long id);

    PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder updatedPurchaseOrder);

    void deletePurchaseOrder(Long id);
}

