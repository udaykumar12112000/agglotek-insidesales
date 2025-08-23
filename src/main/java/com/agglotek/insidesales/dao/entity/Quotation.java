package com.agglotek.insidesales.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "quotations")
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quotation_id")
    private Integer quotationId;

    @Column(name = "quotation_number", length = 50)
    private String quotationNumber;

    @Column(name = "date_of_quotation")
    private LocalDate dateOfQuotation;

    @Column(name = "quotation_due_date")
    private LocalDate quotationDueDate;

    @Column(name = "lead_time", length = 100)
    private String leadTime;

    @Column(name = "scope_of_work", columnDefinition = "TEXT")
    private String scopeOfWork;

    @Column(name = "date_of_proposal")
    private LocalDate dateOfProposal;

    @Column(name = "project_name", length = 200)
    private String projectName;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "is_new_client")
    private Boolean isNewClient;

    @Column(name = "quotation_value", precision = 15, scale = 2)
    private BigDecimal quotationValue;

    @Column(name = "quotation_status", length = 50)
    private String quotationStatus;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "estimator_id")
    private Integer estimatorId;

    @Column(name = "assigned_estimator_id")
    private Integer assignedEstimatorId;

    @Column(name = "project_value", precision = 15, scale = 2)
    private BigDecimal projectValue;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "connection_engineering")
    private Boolean connectionEngineering;

    @Column(name = "connection_engineering_description", columnDefinition = "TEXT")
    private String connectionEngineeringDescription;

    @Column(name = "contact_person_name", length = 100)
    private String contactPersonName;

    @Column(name = "contact_person_number", length = 20)
    private String contactPersonNumber;

    @Column(name = "additional_properties", columnDefinition = "TEXT")
    private String additionalProperties;

    @Column(name = "previous_status", length = 50)
    private String previousStatus;

    // Getters and Setters


    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public String getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(String quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public LocalDate getDateOfQuotation() {
        return dateOfQuotation;
    }

    public void setDateOfQuotation(LocalDate dateOfQuotation) {
        this.dateOfQuotation = dateOfQuotation;
    }

    public String getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(String leadTime) {
        this.leadTime = leadTime;
    }

    public String getScopeOfWork() {
        return scopeOfWork;
    }

    public void setScopeOfWork(String scopeOfWork) {
        this.scopeOfWork = scopeOfWork;
    }

    public BigDecimal getQuotationValue() {
        return quotationValue;
    }

    public void setQuotationValue(BigDecimal quotationValue) {
        this.quotationValue = quotationValue;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getDateOfProposal() {
        return dateOfProposal;
    }

    public void setDateOfProposal(LocalDate dateOfProposal) {
        this.dateOfProposal = dateOfProposal;
    }

    public Boolean getNewClient() {
        return isNewClient;
    }

    public void setNewClient(Boolean newClient) {
        isNewClient = newClient;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getQuotationStatus() {
        return quotationStatus;
    }

    public void setQuotationStatus(String quotationStatus) {
        this.quotationStatus = quotationStatus;
    }

    public Integer getEstimatorId() {
        return estimatorId;
    }

    public void setEstimatorId(Integer estimatorId) {
        this.estimatorId = estimatorId;
    }

    public Integer getAssignedEstimatorId() {
        return assignedEstimatorId;
    }

    public void setAssignedEstimatorId(Integer assignedEstimatorId) {
        this.assignedEstimatorId = assignedEstimatorId;
    }

    public BigDecimal getProjectValue() {
        return projectValue;
    }

    public void setProjectValue(BigDecimal projectValue) {
        this.projectValue = projectValue;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getConnectionEngineering() {
        return connectionEngineering;
    }

    public void setConnectionEngineering(Boolean connectionEngineering) {
        this.connectionEngineering = connectionEngineering;
    }

    public String getConnectionEngineeringDescription() {
        return connectionEngineeringDescription;
    }

    public void setConnectionEngineeringDescription(String connectionEngineeringDescription) {
        this.connectionEngineeringDescription = connectionEngineeringDescription;
    }

    public LocalDate getQuotationDueDate() {
        return quotationDueDate;
    }

    public void setQuotationDueDate(LocalDate quotationDueDate) {
        this.quotationDueDate = quotationDueDate;
    }

    public String getContactPersonNumber() {
        return contactPersonNumber;
    }

    public void setContactPersonNumber(String contactPersonNumber) {
        this.contactPersonNumber = contactPersonNumber;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(String additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    @Override
    public String toString() {
        return "Quotation{" +
                "quotationId=" + quotationId +
                ", quotationNumber='" + quotationNumber + '\'' +
                ", dateOfQuotation=" + dateOfQuotation +
                ", quotationDueDate=" + quotationDueDate +
                ", leadTime='" + leadTime + '\'' +
                ", scopeOfWork='" + scopeOfWork + '\'' +
                ", dateOfProposal=" + dateOfProposal +
                ", projectName='" + projectName + '\'' +
                ", userId=" + userId +
                ", clientId=" + clientId +
                ", isNewClient=" + isNewClient +
                ", quotationValue=" + quotationValue +
                ", quotationStatus='" + quotationStatus + '\'' +
                ", comments='" + comments + '\'' +
                ", estimatorId=" + estimatorId +
                ", assignedEstimatorId=" + assignedEstimatorId +
                ", projectValue=" + projectValue +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", currency='" + currency + '\'' +
                ", connectionEngineering=" + connectionEngineering +
                ", connectionEngineeringDescription='" + connectionEngineeringDescription + '\'' +
                ", contactPersonName='" + contactPersonName + '\'' +
                ", contactPersonNumber='" + contactPersonNumber + '\'' +
                ", additionalProperties='" + additionalProperties + '\'' +
                ", previousStatus='" + previousStatus + '\'' +
                '}';
    }
}
