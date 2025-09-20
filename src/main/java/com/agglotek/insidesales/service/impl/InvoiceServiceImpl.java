package com.agglotek.insidesales.service.impl;


import com.agglotek.insidesales.dao.entity.Invoice;
import com.agglotek.insidesales.dao.entity.Payment;
import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.InvoiceResponseDto;
import com.agglotek.insidesales.repository.InvoiceRepository;
import com.agglotek.insidesales.repository.PaymentRepository;
import com.agglotek.insidesales.repository.ProjectRepository;
import com.agglotek.insidesales.repository.QuotationRepository;
import com.agglotek.insidesales.service.api.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private QuotationRepository quotationRepository;

    @Override
    public Invoice createOrUpdateInvoice(Integer userId, Invoice request) {

        Invoice invoice;

        if (request.getInvoiceId() != null) {
            // Update existing invoice if found, else create new
            invoice = invoiceRepository.findById(request.getInvoiceId()).orElse(new Invoice());
        } else {
            // Create new invoice
            invoice = new Invoice();
            invoice.setInsertTime(LocalDateTime.now());
            invoice.setUpdatedTime(LocalDateTime.now());
        }

        // Set fields from request
        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setInvoiceDate(request.getInvoiceDate());
        invoice.setDueDate(request.getDueDate());
        invoice.setProjectId(request.getProjectId());
        invoice.setUserId(userId);
        invoice.setAmount(request.getAmount());
        invoice.setRemarks(request.getRemarks());

        return invoiceRepository.save(invoice);
    }

    @Override
    public List<InvoiceResponseDto> getInvoices(Integer userId) {
        List<Invoice> invoices = invoiceRepository.findInvoicesByUserId(userId);

        return invoices.stream().map(invoice -> {
            InvoiceResponseDto dto = new InvoiceResponseDto();
            dto.setInvoiceId(invoice.getInvoiceId());
            dto.setInvoiceNumber(invoice.getInvoiceNumber());
            dto.setInvoiceDate(invoice.getInvoiceDate());
            dto.setDueDate(invoice.getDueDate());
            dto.setProjectId(invoice.getProjectId());
            dto.setUserId(invoice.getUserId());
            dto.setAmount(invoice.getAmount());
            dto.setRemarks(invoice.getRemarks());

            // Fetch payments for this invoice
            List<Payment> payments = paymentRepository.findAllByInvoiceId(invoice.getInvoiceId());
            dto.setPayments(payments);

            // Fetch project and related quotation for project name and client ID
            if (invoice.getProjectId() != null) {
                projectRepository.findById(invoice.getProjectId()).ifPresent(project -> {
                    if (project.getQuotationId() != null) {
                        quotationRepository.findById(project.getQuotationId()).ifPresent(quotation -> {
                            dto.setProjectName(quotation.getProjectName());
                            dto.setClientId(quotation.getClientId()); // Add clientId
                        });
                    }
                });
            }

            return dto;
        }).toList();
    }

    @Override
    public void deleteInvoice(Integer invoiceId) {
        if (!invoiceRepository.existsById(invoiceId)) {
            throw new RuntimeException("Invoice not found with id: " + invoiceId);
        }
        invoiceRepository.deleteById(invoiceId);
    }
}
