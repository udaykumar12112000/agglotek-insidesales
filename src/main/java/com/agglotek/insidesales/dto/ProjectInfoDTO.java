package com.agglotek.insidesales.dto;

import com.agglotek.insidesales.dao.entity.Project;

import java.math.BigDecimal;
import java.util.List;

public class ProjectInfoDTO extends Project {
    private String quotationNumber;
    private String date; // mm/dd/yyyy
    private String clientName;
    private String projectName;
    private String country;
    private BigDecimal projectValue;
    private String clientProjectNumber;
    private String purchaseOrder;
    private BigDecimal connPO;
    private Integer clientId;
    private List<String> filesList;

    // Getters and Setters

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(String quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getProjectValue() {
        return projectValue;
    }

    public void setProjectValue(BigDecimal projectValue) {
        this.projectValue = projectValue;
    }

    public String getClientProjectNumber() {
        return clientProjectNumber;
    }

    public List<String> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<String> filesList) {
        this.filesList = filesList;
    }

    @Override
    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    @Override
    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    @Override
    public BigDecimal getConnPO() {
        return connPO;
    }

    public void setConnPO(BigDecimal connPO) {
        this.connPO = connPO;
    }

    public void setClientProjectNumber(String clientProjectNumber) {
        this.clientProjectNumber = clientProjectNumber;
    }

    public ProjectInfoDTO(String quotationNumber, Integer projectId, String projectNumber, String date,
                          String clientName, Integer clientId, String projectName, String country,
                          BigDecimal projectValue, String clientProjectNumber, String purchaseOrder, BigDecimal connPO, String comments) {
        this.quotationNumber = quotationNumber;
        super.setProjectNumber(projectNumber);
        super.setProjectId(projectId);
        this.date = date;
        this.clientName = clientName;
        this.clientId = clientId;
        this.projectName = projectName;
        this.country = country;
        this.projectValue = projectValue;
        this.clientProjectNumber = clientProjectNumber;
        this.purchaseOrder = purchaseOrder;
        this.connPO = connPO;
        super.setComments(comments);
    }

}
