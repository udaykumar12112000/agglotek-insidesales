package com.agglotek.insidesales.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "alter_phone_number", length = 20)
    private String alterPhoneNumber;

    @Column(name = "time_zone", length = 50)
    private String timeZone;

    @Column(name = "available_hrs", length = 100)
    private String availableHrs;

    @Column(name = "our_time", length = 100)
    private String ourTime;

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @Column(name = "details", columnDefinition = "text")
    private String details;

    @Column(updatable = false, insertable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime insertTime;

    @Column(insertable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime updateTime;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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

    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(LocalDateTime insertTime) {
        this.insertTime = insertTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", alterPhoneNumber='" + alterPhoneNumber + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", availableHrs='" + availableHrs + '\'' +
                ", ourTime='" + ourTime + '\'' +
                ", address='" + address + '\'' +
                ", details='" + details + '\'' +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
