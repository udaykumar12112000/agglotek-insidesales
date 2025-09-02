package com.agglotek.insidesales.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "alter_phone_number", length = 20)
    private String alterPhoneNumber;

    @Column(name = "client_type", length = 100)
    private String clientType;

    @Column(name = "time_zone", length = 50)
    private String timeZone;

    @Column(name = "available_hrs", columnDefinition = "text")
    private String availableHrs;

    @Column(name = "our_time", length = 100)
    private String ourTime;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @Column(name = "details", columnDefinition = "text")
    private String details;

    @Column(name = "stake_holders", columnDefinition = "text")
    private String stakeHolders;

    @Column(name = "date_of_entry")
    private String dateOfEntry;

    @Column(name = "update_time", insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedTime;

    @Column(name = "insert_time", insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp insertedTime;

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getStakeHolders() {
        return stakeHolders;
    }

    public void setStakeHolders(String stakeHolders) {
        this.stakeHolders = stakeHolders;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(String dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlterPhoneNumber() {
        return alterPhoneNumber;
    }

    public void setAlterPhoneNumber(String alterPhoneNumber) {
        this.alterPhoneNumber = alterPhoneNumber;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getAvailableHrs() {
        return availableHrs;
    }

    public void setAvailableHrs(String availableHrs) {
        this.availableHrs = availableHrs;
    }

    public String getOurTime() {
        return ourTime;
    }

    public void setOurTime(String ourTime) {
        this.ourTime = ourTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Timestamp getInsertTime() {
        return insertedTime;
    }

    public void setInsertTime(Timestamp insertedTime) {
        this.insertedTime = insertedTime;
    }

    public Timestamp getUpdateTime() {
        return updatedTime;
    }

    public void setUpdateTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", alterPhoneNumber='" + alterPhoneNumber + '\'' +
                ", clientType='" + clientType + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", availableHrs='" + availableHrs + '\'' +
                ", ourTime='" + ourTime + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", details='" + details + '\'' +
                ", stakeHolders='" + stakeHolders + '\'' +
                ", dateOfEntry='" + dateOfEntry + '\'' +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + insertedTime +
                '}';
    }

}
