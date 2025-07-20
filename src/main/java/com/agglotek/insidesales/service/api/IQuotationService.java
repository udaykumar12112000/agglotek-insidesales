package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.Quotation;

public interface IQuotationService {
    boolean existsByClientId(Integer clientId);
    void addQuotation(Quotation quotation);
}
