package com.agglotek.insidesales.dto;

import com.agglotek.insidesales.dao.entity.Project;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProjectDetailsWithQuotationDetails {
    private Integer projectId;
    private Integer quotationId;
    private String comments;
    private LocalDate ifaDate;
    private LocalDate ifcSubmissionDate;
    private LocalDate ifaSubmissionDate;
    private LocalDate plannedSubmittedDate;
    private LocalDateTime updatedTime;
    private LocalDateTime createdTime;
    private Integer projectManagerId;
    private String projectStatus;
    private String projectNumber;
    private String clientProjectNumber;
    private String purchaseOrder;
    private BigDecimal connPO;
    private BigDecimal balanceAmt;

    private String projectName; // extra field from quotations

    public ProjectDetailsWithQuotationDetails(Integer projectId, Integer quotationId, String comments,
                              LocalDate ifaDate, LocalDate ifcSubmissionDate, LocalDate ifaSubmissionDate,
                              LocalDate plannedSubmittedDate, LocalDateTime updatedTime, LocalDateTime createdTime,
                              Integer projectManagerId, String projectStatus, String projectNumber,
                              String clientProjectNumber, String purchaseOrder,
                              BigDecimal connPO, BigDecimal balanceAmt,
                              String projectName) {
        this.projectId = projectId;
        this.quotationId = quotationId;
        this.comments = comments;
        this.ifaDate = ifaDate;
        this.ifcSubmissionDate = ifcSubmissionDate;
        this.ifaSubmissionDate = ifaSubmissionDate;
        this.plannedSubmittedDate = plannedSubmittedDate;
        this.updatedTime = updatedTime;
        this.createdTime = createdTime;
        this.projectManagerId = projectManagerId;
        this.projectStatus = projectStatus;
        this.projectNumber = projectNumber;
        this.clientProjectNumber = clientProjectNumber;
        this.purchaseOrder = purchaseOrder;
        this.connPO = connPO;
        this.balanceAmt = balanceAmt;
        this.projectName = projectName;
    }

    // getters only
    public Integer getProjectId() { return projectId; }
    public Integer getQuotationId() { return quotationId; }
    public String getComments() { return comments; }
    public LocalDate getIfaDate() { return ifaDate; }
    public LocalDate getIfcSubmissionDate() { return ifcSubmissionDate; }
    public LocalDate getIfaSubmissionDate() { return ifaSubmissionDate; }
    public LocalDate getPlannedSubmittedDate() { return plannedSubmittedDate; }
    public LocalDateTime getUpdatedTime() { return updatedTime; }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public Integer getProjectManagerId() { return projectManagerId; }
    public String getProjectStatus() { return projectStatus; }
    public String getProjectNumber() { return projectNumber; }
    public String getClientProjectNumber() { return clientProjectNumber; }
    public String getPurchaseOrder() { return purchaseOrder; }
    public BigDecimal getConnPO() { return connPO; }
    public BigDecimal getBalanceAmt() { return balanceAmt; }
    public String getProjectName() { return projectName; }
}
