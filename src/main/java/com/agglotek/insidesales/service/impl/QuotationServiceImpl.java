package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.QuotationInfoDTO;
import com.agglotek.insidesales.repository.ClientRepository;
import com.agglotek.insidesales.repository.QuotationRepository;
import com.agglotek.insidesales.repository.UserRepository;
import com.agglotek.insidesales.service.api.IQuotationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationServiceImpl implements IQuotationService {

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean existsByClientId(Integer clientId) {
        return quotationRepository.existsByClientId(clientId);
    }

    @Override
    public void addQuotation(Quotation quotation) {
        quotationRepository.save(quotation);
    }

    public List<QuotationInfoDTO> getAllQuotationsByUserId(Integer userId) {
        List<Quotation> quotations = quotationRepository.findByUserId(userId);
        return convertToDTOList(quotations);
    }

    public List<QuotationInfoDTO> getQuotationsByUserIdAndStatus(Integer userId, String quotationStatus) {
        List<Quotation> quotations;

        if (quotationStatus.equalsIgnoreCase("null")) {
            quotations = quotationRepository.findByUserIdAndQuotationStatusIsNull(userId);
        } else {
            quotations = quotationRepository.findByUserIdAndQuotationStatus(userId, quotationStatus);
        }

        return convertToDTOList(quotations);
    }

    private List<QuotationInfoDTO> convertToDTOList(List<Quotation> quotations) {

        if(quotations.isEmpty() || quotations == null)
            return new ArrayList<>();
        return quotations.stream().map(quotation -> {
            QuotationInfoDTO dto = new QuotationInfoDTO();
            BeanUtils.copyProperties(quotation, dto);

            clientRepository.findById(quotation.getClientId()).ifPresent(client -> {
                dto.setClientName(client.getName());
                dto.setCountry(client.getCountry());
            });

            userRepository.findById(quotation.getUserId()).ifPresent(user -> {
                dto.setUserName(user.getName());
            });

            return dto;
        }).collect(Collectors.toList());

    }

    public void updateQuotation(QuotationInfoDTO dto) {
        Quotation quotation = quotationRepository.findById(dto.getQuotationId())
                .orElseThrow(() -> new RuntimeException("Quotation not found with ID: " + dto.getQuotationId()));

        if(dto.getQuotationValue()!=null)
            quotation.setQuotationValue(dto.getQuotationValue());
        if(dto.getQuotationStatus()!=null)
            quotation.setQuotationStatus(dto.getQuotationStatus());
        if(dto.getConnectionEngineering()!=null)
            quotation.setConnectionEngineering(dto.getConnectionEngineering());
        if(dto.getConnectionEngineeringDescription()!=null)
            quotation.setConnectionEngineeringDescription(dto.getConnectionEngineeringDescription());
        if(dto.getScopeOfWork()!=null)
            quotation.setScopeOfWork(dto.getScopeOfWork());
        if(dto.getLeadTime()!=null)
            quotation.setLeadTime(dto.getLeadTime());
        if(dto.getAdditionalProperties()!=null)
            quotation.setAdditionalProperties(dto.getAdditionalProperties());
        if(dto.getDateOfProposal()!=null)
            quotation.setDateOfProposal(dto.getDateOfProposal());

        quotation.setUpdatedTime(LocalDateTime.now());

        quotationRepository.save(quotation);
    }

    @Override
    public List<Quotation> getQuotationsForUser(Integer userId, String roleName) {
        if ("ESTIMATOR_MANAGER".equalsIgnoreCase(roleName)) {
            // Manager sees all quotations
            return quotationRepository.findQuotationsByEstimatorId(userId);
        } else if ("ESTIMATOR".equalsIgnoreCase(roleName)) {
            // Estimator sees only assigned to them
            return quotationRepository.findByAssignedEstimatorId(userId);
        }
        // If other role — no quotations
        return Collections.emptyList();
    }

    public void updateAssignedEstimator(Integer quotationId, Integer newEstimatorId) {
        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new RuntimeException("Quotation not found"));

        quotation.setAssignedEstimatorId(newEstimatorId);
        quotationRepository.save(quotation);
    }

}
