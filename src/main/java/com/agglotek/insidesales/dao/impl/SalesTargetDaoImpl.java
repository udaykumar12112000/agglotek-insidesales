package com.agglotek.insidesales.dao.impl;


import com.agglotek.insidesales.dao.api.ISalesTargetDao;
import com.agglotek.insidesales.repository.SalesTargetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesTargetDaoImpl implements ISalesTargetDao {

    @Autowired
    private SalesTargetsRepository salesTargetsRepository;

    @Override
    public int getMonthlyTarget(Integer userId) {
        return salesTargetsRepository.getMonthlyTarget(userId);
    }

    @Override
    public int getYearlyTarget(Integer userId) {
        return salesTargetsRepository.getYearlyTarget(userId);
    }
}
