package com.agglotek.insidesales.dao.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "client_convo")
public class ClientConvo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientConvoId;

    private Integer clientId;

    private Integer userId;

    @Column(columnDefinition = "text")
    private String callConvo; // stored as JSON string like: {"2025-07-18": "Follow-up call"}

    @Column(columnDefinition = "text")
    private String statusUpdate; // stored as JSON string like: {"Call On": "2025-07-19"}

    private String remarks;

    // Getters and Setters


    public Integer getClientConvoId() {
        return clientConvoId;
    }

    public void setClientConvoId(Integer clientConvoId) {
        this.clientConvoId = clientConvoId;
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

    public String getCallConvo() {
        return callConvo;
    }

    public void setCallConvo(String callConvo) {
        this.callConvo = callConvo;
    }

    public String getStatusUpdate() {
        return statusUpdate;
    }

    public void setStatusUpdate(String statusUpdate) {
        this.statusUpdate = statusUpdate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
