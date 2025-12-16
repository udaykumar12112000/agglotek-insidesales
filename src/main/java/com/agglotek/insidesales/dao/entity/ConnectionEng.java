package com.agglotek.insidesales.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "connection_eng")
public class ConnectionEng {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long connectionEngId;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "contact_person_name", columnDefinition = "TEXT")
    private String contactPersonName;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "remark")
    private String remark;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    // Getters and Setters

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getConnectionEngId() {
        return connectionEngId;
    }

    public void setConnectionEngId(Long connectionEngId) {
        this.connectionEngId = connectionEngId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ConnectionEng{" +
                "connectionEngId=" + connectionEngId +
                ", companyName='" + companyName + '\'' +
                ", contactPersonName='" + contactPersonName + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", remark='" + remark + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
