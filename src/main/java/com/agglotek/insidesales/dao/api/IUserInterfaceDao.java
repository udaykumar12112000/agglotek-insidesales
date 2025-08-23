package com.agglotek.insidesales.dao.api;

import com.agglotek.insidesales.dao.entity.Quotation;

import java.util.List;
import java.util.Map;

public interface IUserInterfaceDao {
    Integer getTotalNumberOfBidsRecievedThisMonth(Integer userId);

    Integer getTotalNumberOfBidsYetToUpdateThisMonth(Integer userId, String status);

    Integer getTotalNumberOfProjectsAllotedThisMonth(Integer userId, String allotted);

    Integer getTotalNumberOfBidsRecievedThisYear(Integer userId);

    Integer getTotalNewClientsAchievedThisYear(Integer userId);

    Integer getTotalNumberOfProjectsAllotedThisYear(Integer userId, String allotted);

    Integer getmonthlyTargetAchieved(Integer userId, String status);

    Integer yearlyTargetAchieved(Integer userId, String status);

    List<Quotation> getAllNotificationsToTheUser(Integer userId);
}
