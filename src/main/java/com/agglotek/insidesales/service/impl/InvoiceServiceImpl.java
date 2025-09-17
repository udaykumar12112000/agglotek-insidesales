package com.agglotek.insidesales.service.impl;


import com.agglotek.insidesales.dao.entity.Invoice;
import com.agglotek.insidesales.repository.InvoiceRepository;
import com.agglotek.insidesales.service.api.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice createOrUpdateInvoice(Invoice request) {

        Invoice invoice;

        if (request.getInvoiceId() != null) {
            // Update existing invoice if found, else create new
            invoice = invoiceRepository.findById(request.getInvoiceId()).orElse(new Invoice());
        } else {
            // Create new invoice
            invoice = new Invoice();
        }

        // Set fields from request
        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setInvoiceDate(request.getInvoiceDate());
        invoice.setDueDate(request.getDueDate());
        invoice.setProjectId(request.getProjectId());
        invoice.setUserId(request.getUserId());
        invoice.setAmount(request.getAmount());
        invoice.setRemarks(request.getRemarks());

        return invoiceRepository.save(invoice);
    }
}
