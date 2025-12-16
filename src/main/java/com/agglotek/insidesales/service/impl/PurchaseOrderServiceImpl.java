package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.QuotationInfoDTO;
import com.agglotek.insidesales.repository.ProjectRepository;
import com.agglotek.insidesales.service.api.IProjectService;
import com.agglotek.insidesales.service.api.IPurchaseOrderService;
import com.agglotek.insidesales.dao.entity.PurchaseOrder;
import com.agglotek.insidesales.repository.PurchaseOrderRepository;
import com.agglotek.insidesales.service.api.IQuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
        // Generate PO number
        String yearPart = String.valueOf(LocalDate.now().getYear()).substring(2); // e.g. "25"
        Long count = purchaseOrderRepository.countByYearPart(yearPart);

        // Increment serial number (format: PYY-XXX)
        long nextSerial = count + 1;
        String serialPart = String.format("%03d", nextSerial);
        String poNumber = "P" + yearPart + "-" + serialPart;

        purchaseOrder.setPoNumber(poNumber);

        Project project = projectRepository.getByProjectId(purchaseOrder.getProjectId());
        System.out.println(("JAXX ::: PROJECT :: " + project));
        project.setConnPO(BigDecimal.valueOf(purchaseOrder.getValue()));
        projectService.updateProject(project);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public Optional<PurchaseOrder> getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id);
    }

    @Override
    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder updatedPurchaseOrder) {
        return purchaseOrderRepository.findById(id).map(existing -> {
            existing.setIssueDate(updatedPurchaseOrder.getIssueDate());
            existing.setScope(updatedPurchaseOrder.getScope());
            existing.setDueBy(updatedPurchaseOrder.getDueBy());
            existing.setValue(updatedPurchaseOrder.getValue());
            existing.setProjectId(updatedPurchaseOrder.getProjectId());
            return purchaseOrderRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Purchase Order not found with ID " + id));
    }

    @Override
    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }
}
