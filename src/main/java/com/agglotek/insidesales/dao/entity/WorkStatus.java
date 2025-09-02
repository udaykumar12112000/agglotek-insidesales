package com.agglotek.insidesales.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "work_status")
public class WorkStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workStatusId;

    @Column(nullable = false)
    private Integer userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;

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

    public void setProjectsReceived(Integer projectsReceived) {
        this.projectsReceived = projectsReceived;
    }
}
