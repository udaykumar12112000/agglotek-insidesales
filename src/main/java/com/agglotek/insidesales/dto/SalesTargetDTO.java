package com.agglotek.insidesales.dto;

import com.agglotek.insidesales.dao.entity.SalesTarget;
import jakarta.persistence.Column;

import java.math.BigDecimal;

public class SalesTargetDTO extends SalesTarget {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SalesTargetDTO(SalesTarget target, String userName) {
        this.userName = userName;
        this.setUserId(target.getUserId());
        this.setYear(target.getYear());
        this.setJan(target.getJan());
        this.setFeb(target.getFeb());
        this.setMar(target.getMar());
        this.setApr(target.getApr());
        this.setMay(target.getMay());
        this.setJun(target.getJun());
        this.setJul(target.getJul());
        this.setAug(target.getAug());
        this.setSep(target.getSep());
        this.setOct(target.getOct());
        this.setNov(target.getNov());
        this.setDec(target.getDec());

    }

}
