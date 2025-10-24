package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.QuotationInfoDTO;

import java.util.List;

public interface IQuotationService {
    boolean existsByClientId(Integer clientId);
    void addQuotation(Quotation quotation);
    List<QuotationInfoDTO> getAllQuotationsByUserId(Integer userId);
    List<QuotationInfoDTO> getQuotationsByUserIdAndStatus(Integer userId, List<String> statusList);

    void updateQuotation(QuotationInfoDTO dto);

    public List<QuotationInfoDTO> getQuotationsForUser(Integer userId, String roleName);

    public void updateAssignedEstimator(Integer quotationId, Integer newEstimatorId);

    public List<QuotationInfoDTO> getAllQuotations();

    public List<QuotationInfoDTO> convertToDTOList(List<Quotation> quotations);
}
