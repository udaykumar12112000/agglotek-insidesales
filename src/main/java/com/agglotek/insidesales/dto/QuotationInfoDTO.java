package com.agglotek.insidesales.dto;

import com.agglotek.insidesales.dao.entity.Quotation;
import java.util.List;

public class QuotationInfoDTO extends Quotation {

    private String clientName;
    private String country;
    private String userName;
    List<String> filesList;

    public QuotationInfoDTO() {
        super();
    }

    public QuotationInfoDTO(String clientName, String country, String userName) {
        this.clientName = clientName;
        this.country = country;
        this.userName = userName;
    }

    // Getters and Setters

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<String> filesList) {
        this.filesList = filesList;
    }
}
