package com.agglotek.insidesales.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poId;

    @Column(name = "po_number", unique = true, nullable = false)
    private String poNumber;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "scope", columnDefinition = "TEXT")
    private String scope;

    @Column(name = "due_by")
    private LocalDate dueBy;

    @Column(name = "value")
    private Long value;

    @Column(name = "project_id")
    private Integer projectId;

    @Transient
    @JsonProperty("buyerName")
    private String buyerName;

    @Transient
    @JsonProperty("projectName")
    private String projectName;

    @Transient
    @JsonProperty("clientName")
    private String clientName;


    @Transient
    @JsonProperty("clientId")
    private Integer clientId;

    @Transient
    @JsonProperty("projectNumber")
    private String projectNumber;

    @Transient
    @JsonProperty("connEng")
    private ConnectionEng connEng;

    @Transient
    @JsonProperty("filesList")
    private List<String> filesList;

    // --- Getters and Setters ---


    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ConnectionEng getConnEng() {
        return connEng;
    }

    public void setConnEng(ConnectionEng connEng) {
        this.connEng = connEng;
    }

    public List<String> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<String> filesList) {
        this.filesList = filesList;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Long getPoId() {
        return poId;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public LocalDate getDueBy() {
        return dueBy;
    }

    public void setDueBy(LocalDate dueBy) {
        this.dueBy = dueBy;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
