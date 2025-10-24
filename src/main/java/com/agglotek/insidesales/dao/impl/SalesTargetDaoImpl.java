package com.agglotek.insidesales.dao.impl;


import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.dao.api.ISalesTargetDao;
import com.agglotek.insidesales.dao.entity.SalesTarget;
import com.agglotek.insidesales.dto.SalesTargetDTO;
import com.agglotek.insidesales.repository.SalesTargetsRepository;
import com.agglotek.insidesales.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalesTargetDaoImpl implements ISalesTargetDao {

    @Autowired
    private SalesTargetsRepository salesTargetsRepository;

    private IUserService userService;

    public SalesTargetDaoImpl(@Lazy IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Integer getMonthlyTarget(Integer userId) {
        return salesTargetsRepository.getMonthlyTarget(userId);
    }

    @Override
    public Integer getYearlyTarget(Integer userId) {
        return salesTargetsRepository.getYearlyTarget(userId);
    }


    @Override
    public List<SalesTarget> saveUserTargets(List<SalesTarget> userMonthlyTargets) {
        List<SalesTarget> updatedList = new ArrayList<>();

        for (SalesTarget monthlyTarget : userMonthlyTargets) {
            SalesTarget target = salesTargetsRepository
                    .findByUserIdAndYear(monthlyTarget.getUserId(), monthlyTarget.getYear())
                    .orElse(new SalesTarget());

            target.setUserId(monthlyTarget.getUserId());
            target.setYear(monthlyTarget.getYear());
            target.setJan(monthlyTarget.getJan());
            target.setFeb(monthlyTarget.getFeb());
            target.setMar(monthlyTarget.getMar());
            target.setApr(monthlyTarget.getApr());
            target.setMay(monthlyTarget.getMay());
            target.setJun(monthlyTarget.getJun());
            target.setJul(monthlyTarget.getJul());
            target.setAug(monthlyTarget.getAug());
            target.setSep(monthlyTarget.getSep());
            target.setOct(monthlyTarget.getOct());
            target.setNov(monthlyTarget.getNov());
            target.setDec(monthlyTarget.getDec());

            SalesTarget saved = salesTargetsRepository.save(target);
            updatedList.add(saved);
        }
        return updatedList;

    }

    @Override
    public List<SalesTargetDTO> getAllSalestargets(BigDecimal year) {

        List<SalesTargetDTO> usersWithtargetsList = new ArrayList<>();
       List<SalesTarget> salesTargetList = salesTargetsRepository.findByYear(year);
       for(SalesTarget salesTarget : salesTargetList) {
           SalesTargetDTO dto = new SalesTargetDTO(salesTarget, userService.getUserByUserId(salesTarget.getUserId()).get(0).getName());
           usersWithtargetsList.add(dto);
       }
        return usersWithtargetsList;
    }
}
