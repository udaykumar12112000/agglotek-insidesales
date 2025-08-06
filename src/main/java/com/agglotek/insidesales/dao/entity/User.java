package com.agglotek.insidesales.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "agglo_user_id", unique = true)
    private String aggloUserId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "designation", length = 100)
    private String designation;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "sup_user_id")
    private Integer supUserId;

    @Transient
    @JsonProperty("editorRoleName")
    private String editorRoleName;

    public String getEditorRoleName() {
        return editorRoleName;
    }

    public void setEditorRoleName(String editorRoleName) {
        this.editorRoleName = editorRoleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAggloUserId() {
        return aggloUserId;
    }

    public void setAggloUserId(String aggloUserId) {
        this.aggloUserId = aggloUserId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // --- Getters and Setters ---

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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getSupUserId() {
        return supUserId;
    }

    public void setSupUserId(Integer subUserId) {
        this.supUserId = subUserId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", aggloUserId='" + aggloUserId + '\'' +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roleId=" + roleId +
                ", supUserId=" + supUserId +
                ", editorRoleName='" + editorRoleName + '\'' +
                '}';
    }
}