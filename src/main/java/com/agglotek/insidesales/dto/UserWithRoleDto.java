package com.agglotek.insidesales.dto;

public class UserWithRoleDto {

    private Integer userId;
    private String aggloUserId;
    private String name;
    private String designation;
    private String department;
    private String email;
    private String phoneNumber;
    private Integer roleId;
    private String roleName;
    private Integer supUserId;

    // Constructor
    public UserWithRoleDto(Integer userId, String aggloUserId, String name, String designation,
                           String department, String email, String phoneNumber,
                           Integer roleId, String roleName, Integer supUserId) {
        this.userId = userId;
        this.aggloUserId = aggloUserId;
        this.name = name;
        this.designation = designation;
        this.department = department;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
        this.roleName = roleName;
        this.supUserId = supUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAggloUserId() {
        return aggloUserId;
    }

    public void setAggloUserId(String aggloUserId) {
        this.aggloUserId = aggloUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getSupUserId() {
        return supUserId;
    }

    public void setSupUserId(Integer supUserId) {
        this.supUserId = supUserId;
    }

}
