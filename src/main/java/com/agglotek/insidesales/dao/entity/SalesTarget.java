package com.agglotek.insidesales.dao.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sales_targets")
public class SalesTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_targets_id")
    private Integer salesTargetsId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "year", precision = 15, scale = 2)
    private BigDecimal year;

    @Column(name = "month", length = 10)
    private String month;

    // Getters and Setters

    public Integer getSalesTargetsId() {
        return salesTargetsId;
    }

    public void setSalesTargetsId(Integer salesTargetsId) {
        this.salesTargetsId = salesTargetsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getYear() {
        return year;
    }

    public void setYear(BigDecimal year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "SalesTarget{" +
                "salesTargetsId=" + salesTargetsId +
                ", userId=" + userId +
                ", year=" + year +
                ", month='" + month + '\'' +
                '}';
    }
}

