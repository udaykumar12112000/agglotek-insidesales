package com.agglotek.insidesales.dao.entity;

import jakarta.persistence.*;
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

    // Getters and Setters

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
                '}';
    }
}
