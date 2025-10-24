package com.agglotek.insidesales.dao.api;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.dao.entity.SalesTarget;
import com.agglotek.insidesales.dto.SalesTargetDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ISalesTargetDao {

    Integer getMonthlyTarget(Integer userId);

    Integer getYearlyTarget(Integer userId);

    List<SalesTarget> saveUserTargets(List<SalesTarget> userMonthlyTargets);

    List<SalesTargetDTO> getAllSalestargets(BigDecimal year);
}
