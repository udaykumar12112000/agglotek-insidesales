package com.agglotek.insidesales.dto;

import com.agglotek.insidesales.dao.entity.Client;
import com.agglotek.insidesales.dao.entity.ClientConvo;

import java.util.Map;

public class ClientConvoDTO extends Client {

    private Map<String, String> callConvo;
    private Map<String, String> statusUpdate;

    private String remarks;

    private Integer clientConvoId;

    public Integer getClientConvoId() {
        return clientConvoId;
    }

    public void setClientConvoId(Integer clientConvoId) {
        this.clientConvoId = clientConvoId;
    }

    public Map<String, String> getCallConvo() {
        return callConvo;
    }

    public void setCallConvo(Map<String, String> callConvo) {
        this.callConvo = callConvo;
    }

    public Map<String, String> getStatusUpdate() {
        return statusUpdate;
    }

    public void setStatusUpdate(Map<String, String> statusUpdate) {
        this.statusUpdate = statusUpdate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ClientConvoDTO() {
        super();
    }

    public ClientConvoDTO(Integer clientId, Integer userId, Map<String, String> callConvo, Map<String, String> statusUpdate, String remarks) {
        this.setClientId(clientId);
        this.setUserId(userId);
        this.callConvo = callConvo;
        this.statusUpdate = statusUpdate;
        this.remarks = remarks;
    }
}
