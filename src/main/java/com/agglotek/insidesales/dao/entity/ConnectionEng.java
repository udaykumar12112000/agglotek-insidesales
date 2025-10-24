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

    @Column(name = "contact_person_name")
    private String contactPersonName;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "mail_address")
    private String mailAddress;

    @Column(name = "country")
    private String country;

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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
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
}
