package com.agglotek.insidesales.dao.api;

public interface ISalesTargetDao {

    int getMonthlyTarget(Integer userId);

    int getYearlyTarget(Integer userId);
}
