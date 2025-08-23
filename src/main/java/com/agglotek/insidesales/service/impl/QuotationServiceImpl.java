package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.constants.AppConstants;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.QuotationInfoDTO;
import com.agglotek.insidesales.repository.ClientRepository;
import com.agglotek.insidesales.repository.QuotationRepository;
import com.agglotek.insidesales.repository.UserRepository;
import com.agglotek.insidesales.service.api.IProjectService;
import com.agglotek.insidesales.service.api.IQuotationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QuotationServiceImpl implements IQuotationService {

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IProjectService projectService;

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

    public List<QuotationInfoDTO> getQuotationsByUserIdAndStatus(Integer userId, List<String> quotationStatuses) {
        List<Quotation> quotations = new ArrayList<>();

//        if (quotationStatus.equalsIgnoreCase(AppConstants.NULL)) {
//            quotations = quotationRepository.findByUserIdAndQuotationStatusIsNull(userId);
//        } else {
//            quotations = quotationRepository.findByUserIdAndQuotationStatus(userId, quotationStatus);
//        }

        if (quotationStatuses != null && !quotationStatuses.isEmpty()) {
            List<String> filteredStatuses = quotationStatuses.stream()
                    .filter(status -> !AppConstants.NULL.equalsIgnoreCase(status))
                    .collect(Collectors.toList());

            if (!filteredStatuses.isEmpty()) {
                quotations.addAll(quotationRepository.findByUserIdAndQuotationStatusIn(userId, filteredStatuses));
            }

            if (quotationStatuses.stream().anyMatch(s -> AppConstants.NULL.equalsIgnoreCase(s))) {
                quotations.addAll(quotationRepository.findByUserIdAndQuotationStatusIsNull(userId));
            }
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
        if(dto.getPreviousStatus()!=null)
            quotation.setPreviousStatus(dto.getPreviousStatus());
        if(dto.getQuotationStatus()!=null) {
            quotation.setQuotationStatus(dto.getQuotationStatus());

            if (AppConstants.ALLOTTED.equalsIgnoreCase(dto.getQuotationStatus())) {
                projectService.createProjectFromQuotation(quotation);
            }
        }
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

        quotation.setDateOfProposal(LocalDate.now());
        quotation.setUpdatedTime(LocalDateTime.now());

        quotationRepository.save(quotation);
    }

    @Override
    public List<QuotationInfoDTO> getQuotationsForUser(Integer userId, String roleName) {
        List<Quotation> quotationList = new ArrayList<>();
        if (AppConstants.ESTIMATOR_MANAGER.equalsIgnoreCase(roleName)) {
            // Manager sees all quotations
            quotationList = quotationRepository.findQuotationsByEstimatorId(userId);
        } else if (AppConstants.ESTIMATOR.equalsIgnoreCase(roleName)) {
            // Estimator sees only assigned to them
            quotationList = quotationRepository.findByAssignedEstimatorId(userId);
        }
        // If other role — no quotations
        return convertToDTOList(quotationList);
    }

    public void updateAssignedEstimator(Integer quotationId, Integer newEstimatorId) {
        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new RuntimeException("Quotation not found"));

        quotation.setAssignedEstimatorId(newEstimatorId);
        quotationRepository.save(quotation);
    }

    @Override
    public List<QuotationInfoDTO> getAllQuotations() {
        List<Quotation> quotations = quotationRepository.getAllQuotations();
        return convertToDTOList(quotations);
    }

}
