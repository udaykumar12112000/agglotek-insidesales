package com.agglotek.insidesales.dao.api;

public interface ISalesTargetDao {

    Integer getMonthlyTarget(Integer userId);

    Integer getYearlyTarget(Integer userId);
}
