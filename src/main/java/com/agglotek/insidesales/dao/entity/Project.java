package com.agglotek.insidesales.dao.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "quotation_id")
    private Integer quotationId;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "ifa_date")
    private LocalDate ifaDate;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "project_manager_id")
    private Integer projectManagerId;

    @Column(name = "project_status", length = 50)
    private String projectStatus;

    @Column(name = "planned_submitted_date")
    private LocalDate plannedSubmittedDate;

    @Column(name = "ifc_submission_date")
    private LocalDate ifcSubmissionDate;

    @Column(name = "ifa_submission_date")
    private LocalDate ifaSubmissionDate;

    @Column(name = "project_number")
    private String projectNumber;

    @Column(name = "client_job_number", length = 100)
    private String clientProjectNumber;

    @Column(name = "purchase_order", columnDefinition = "TEXT")
    private String purchaseOrder;

    @Column(name = "conn_po", precision = 15, scale = 2)
    private BigDecimal connPO;

    @Column(name = "balance_amt", precision = 15, scale = 2)
    private BigDecimal balanceAmt;

    // Getters and Setters

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getClientProjectNumber() {
        return clientProjectNumber;
    }

    public void setClientProjectNumber(String clientProjectNumber) {
        this.clientProjectNumber = clientProjectNumber;
    }

    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public LocalDate getIfaDate() {
        return ifaDate;
    }

    public void setIfaDate(LocalDate ifaDate) {
        this.ifaDate = ifaDate;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(Integer projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public LocalDate getPlannedSubmittedDate() {
        return plannedSubmittedDate;
    }

    public void setPlannedSubmittedDate(LocalDate plannedSubmittedDate) {
        this.plannedSubmittedDate = plannedSubmittedDate;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public LocalDate getIfcSubmissionDate() {
        return ifcSubmissionDate;
    }

    public void setIfcSubmissionDate(LocalDate ifcSubmissionDate) {
        this.ifcSubmissionDate = ifcSubmissionDate;
    }

    public LocalDate getIfaSubmissionDate() {
        return ifaSubmissionDate;
    }

    public void setIfaSubmissionDate(LocalDate ifaSubmissionDate) {
        this.ifaSubmissionDate = ifaSubmissionDate;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public BigDecimal getConnPO() {
        return connPO;
    }

    public void setConnPO(BigDecimal connPO) {
        this.connPO = connPO;
    }

    public BigDecimal getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(BigDecimal balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", quotationId=" + quotationId +
                ", comments='" + comments + '\'' +
                ", ifaDate=" + ifaDate +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", projectManagerId=" + projectManagerId +
                ", projectStatus='" + projectStatus + '\'' +
                ", plannedSubmittedDate=" + plannedSubmittedDate +
                ", ifcSubmissionDate=" + ifcSubmissionDate +
                ", ifaSubmissionDate=" + ifaSubmissionDate +
                ", projectNumber='" + projectNumber + '\'' +
                ", clientProjectNumber='" + clientProjectNumber + '\'' +
                ", purchaseOrder='" + purchaseOrder + '\'' +
                ", connPO=" + connPO + '\'' +
                ", balanceAmt=" + balanceAmt + '\'' +
                '}';
    }
}
