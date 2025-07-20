package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.repository.QuotationRepository;
import com.agglotek.insidesales.service.api.IQuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuotationServiceImpl implements IQuotationService {

    @Autowired
    private QuotationRepository quotationRepository;

    @Override
    public boolean existsByClientId(Integer clientId) {
        return quotationRepository.existsByClientId(clientId);
    }

    @Override
    public void addQuotation(Quotation quotation) {
        quotationRepository.save(quotation);
    }

}
