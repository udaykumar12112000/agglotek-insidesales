package com.agglotek.insidesales.dao.api;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.dao.entity.SalesTarget;

import java.util.List;

public interface ISalesTargetDao {

    Integer getMonthlyTarget(Integer userId);

    Integer getYearlyTarget(Integer userId);

    List<SalesTarget> saveUserTargets(List<SalesTarget> userMonthlyTargets);

}
