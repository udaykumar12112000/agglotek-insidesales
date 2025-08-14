package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.service.impl.NotificationDTO;

import java.util.List;
import java.util.Map;

public interface IUserService {
    User addUser(User user);
    List<User> getUserByUserId(Integer userId);
    List<User> getUsersByRoleId(Integer roleId);
    List<User> getAllUsers();
    User findByEmail(String email);

    Map<String, Integer> getUserSummaryDetaialsForSales(Integer userId);

    List<NotificationDTO> getUserNotifications(Integer userId);

    void updateQuotationStatus(String status, Integer quotationId);
    List<User> getUserReportees(Integer supUserId);
}