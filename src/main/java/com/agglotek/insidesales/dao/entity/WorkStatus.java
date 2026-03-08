package com.agglotek.insidesales.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "work_status", uniqueConstraints = {
        @UniqueConstraint(name = "unique_work_status_user_date", columnNames = {"user_id", "date"})
})
public class WorkStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workStatusId;

    @Column(nullable = false)
    private Integer userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Transient
    @JsonProperty("month")
    private String month;

    @Transient
    @JsonProperty("year")
    private String year;

    private Integer noOfCalls = 0;
    private Integer samplesSend = 0;
    private Integer bidsReceived = 0;
    private Integer projectsReceived = 0;

    // Getters and Setters

    public Integer getWorkStatusId() {
        return workStatusId;
    }

    public void setWorkStatusId(Integer workStatusId) {
        this.workStatusId = workStatusId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getNoOfCalls() {
        return noOfCalls;
    }

    public void setNoOfCalls(Integer noOfCalls) {
        this.noOfCalls = noOfCalls;
    }

    public Integer getSamplesSend() {
        return samplesSend;
    }

    public void setSamplesSend(Integer samplesSend) {
        this.samplesSend = samplesSend;
    }

    public Integer getBidsReceived() {
        return bidsReceived;
    }

    public void setBidsReceived(Integer bidsReceived) {
        this.bidsReceived = bidsReceived;
    }

    public Integer getProjectsReceived() {
        return projectsReceived;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setProjectsReceived(Integer projectsReceived) {
        this.projectsReceived = projectsReceived;
    }
}
