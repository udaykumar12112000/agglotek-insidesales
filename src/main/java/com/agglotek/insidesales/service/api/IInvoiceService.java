package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.Invoice;

public interface IInvoiceService {
    Invoice createOrUpdateInvoice(Invoice request);
}
