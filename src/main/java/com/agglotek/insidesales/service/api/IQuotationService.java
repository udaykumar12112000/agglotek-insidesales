package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.QuotationInfoDTO;

import java.util.List;

public interface IQuotationService {
    boolean existsByClientId(Integer clientId);
    void addQuotation(Quotation quotation);
    List<QuotationInfoDTO> getAllQuotationsByUserId(Integer userId);
    List<QuotationInfoDTO> getQuotationsByUserIdAndStatus(Integer userId, String quotationStatus);

    void updateQuotation(QuotationInfoDTO dto);
}
