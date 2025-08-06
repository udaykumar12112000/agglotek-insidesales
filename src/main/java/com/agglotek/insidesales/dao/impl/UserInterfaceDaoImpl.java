package com.agglotek.insidesales.dao.impl;

import com.agglotek.insidesales.dao.api.IUserInterfaceDao;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.repository.QuotationRepository;
import com.agglotek.insidesales.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserInterfaceDaoImpl implements IUserInterfaceDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuotationRepository quotationRepository;

    @Override
    public int getTotalNumberOfBidsRecievedThisMonth(Integer userId) {
        return userRepository.getTotalNumberOfBidsRecievedThisMonth(userId);
    }

    @Override
    public int getTotalNumberOfBidsYetToUpdateThisMonth(Integer userId, String status) {
        return userRepository.getTotalNumberOfBidsYetToUpdateThisMonth(userId, status);
    }

    @Override
    public int getTotalNumberOfProjectsAllotedThisMonth(Integer userId, String allotted) {
        return userRepository.getTotalNumberOfProjectsAllotedThisMonth(userId, allotted);
    }

    @Override
    public int getTotalNumberOfBidsRecievedThisYear(Integer userId) {
        return userRepository.getTotalNumberOfBidsRecievedThisYear(userId);
    }

    @Override
    public int getTotalNewClientsAchievedThisYear(Integer userId) {
        return userRepository.getTotalNewClientsAchievedThisYear(userId);
    }

    @Override
    public int getTotalNumberOfProjectsAllotedThisYear(Integer userId, String allotted) {
        return userRepository.getTotalNumberOfProjectsAllotedThisYear(userId, allotted);
    }

    @Override
    public int getmonthlyTargetAchieved(Integer userId, String status) {
        return getTotalNumberOfProjectsAllotedThisMonth(userId, status);
    }

    @Override
    public int yearlyTargetAchieved(Integer userId, String status) {
        return getTotalNumberOfProjectsAllotedThisYear(userId, status);
    }

    @Override
    public List<Quotation> getAllNotificationsToTheUser(Integer userId) {
        return quotationRepository.getAllNotificationsToTheUser(userId);
    }
}
