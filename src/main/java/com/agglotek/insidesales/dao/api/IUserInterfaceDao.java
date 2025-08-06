package com.agglotek.insidesales.dao.api;

import com.agglotek.insidesales.dao.entity.Quotation;

import java.util.List;
import java.util.Map;

public interface IUserInterfaceDao {
    int getTotalNumberOfBidsRecievedThisMonth(Integer userId);

    int getTotalNumberOfBidsYetToUpdateThisMonth(Integer userId, String status);

    int getTotalNumberOfProjectsAllotedThisMonth(Integer userId, String allotted);

    int getTotalNumberOfBidsRecievedThisYear(Integer userId);

    int getTotalNewClientsAchievedThisYear(Integer userId);

    int getTotalNumberOfProjectsAllotedThisYear(Integer userId, String allotted);

    int getmonthlyTargetAchieved(Integer userId, String status);

    int yearlyTargetAchieved(Integer userId, String status);

    List<Quotation> getAllNotificationsToTheUser(Integer userId);
}
