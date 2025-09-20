package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.Invoice;
import com.agglotek.insidesales.dto.InvoiceResponseDto;

import java.util.List;

public interface IInvoiceService {
    Invoice createOrUpdateInvoice(Integer userid, Invoice request);

    List<InvoiceResponseDto> getInvoices(Integer userId);

    void deleteInvoice(Integer invoiceId);
}
