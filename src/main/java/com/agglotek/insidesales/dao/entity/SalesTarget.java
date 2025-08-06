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

    @Column(name = "jan")
    private Integer jan;

    @Column(name = "feb")
    private Integer feb;

    @Column(name = "mar")
    private Integer mar;

    @Column(name = "apr")
    private Integer apr;

    @Column(name = "may")
    private Integer may;

    @Column(name = "jun")
    private Integer jun;

    @Column(name = "jul")
    private Integer jul;

    @Column(name = "aug")
    private Integer aug;

    @Column(name = "sep")
    private Integer sep;

    @Column(name = "oct")
    private Integer oct;

    @Column(name = "nov")
    private Integer nov;

    @Column(name = "dec")
    private Integer dec;

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

    public Integer getJan() {
        return jan;
    }

    public void setJan(Integer jan) {
        this.jan = jan;
    }

    public Integer getFeb() {
        return feb;
    }

    public void setFeb(Integer feb) {
        this.feb = feb;
    }

    public Integer getMar() {
        return mar;
    }

    public void setMar(Integer mar) {
        this.mar = mar;
    }

    public Integer getApr() {
        return apr;
    }

    public void setApr(Integer apr) {
        this.apr = apr;
    }

    public Integer getMay() {
        return may;
    }

    public void setMay(Integer may) {
        this.may = may;
    }

    public Integer getJun() {
        return jun;
    }

    public void setJun(Integer jun) {
        this.jun = jun;
    }

    public Integer getJul() {
        return jul;
    }

    public void setJul(Integer jul) {
        this.jul = jul;
    }

    public Integer getAug() {
        return aug;
    }

    public void setAug(Integer aug) {
        this.aug = aug;
    }

    public Integer getSep() {
        return sep;
    }

    public void setSep(Integer sep) {
        this.sep = sep;
    }

    public Integer getOct() {
        return oct;
    }

    public void setOct(Integer oct) {
        this.oct = oct;
    }

    public Integer getNov() {
        return nov;
    }

    public void setNov(Integer nov) {
        this.nov = nov;
    }

    public Integer getDec() {
        return dec;
    }

    public void setDec(Integer dec) {
        this.dec = dec;
    }

    @Override
    public String toString() {
        return "SalesTarget{" +
                "salesTargetsId=" + salesTargetsId +
                ", userId=" + userId +
                ", year=" + year +
                ", jan=" + jan +
                ", feb=" + feb +
                ", mar=" + mar +
                ", apr=" + apr +
                ", may=" + may +
                ", jun=" + jun +
                ", jul=" + jul +
                ", aug=" + aug +
                ", sep=" + sep +
                ", oct=" + oct +
                ", nov=" + nov +
                ", dec=" + dec +
                '}';
    }
}


