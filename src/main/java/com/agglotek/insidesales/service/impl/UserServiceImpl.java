package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.api.ISalesTargetDao;
import com.agglotek.insidesales.dao.api.IUserInterfaceDao;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.repository.QuotationRepository;
import com.agglotek.insidesales.repository.UserRepository;
import com.agglotek.insidesales.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private IUserInterfaceDao userInterfaceDao;

    @Autowired
    private ISalesTargetDao salesTargetDao;

    @Autowired
    private QuotationRepository quotationRepository;

    @Override
    public User addUser(User user) {
        return usersRepository.save(user);
    }

    @Override
    public List<User> getUserByUserId(Integer userId) {
        return usersRepository.findByUserId(userId);
    }

    @Override
    public List<User> getUsersByRoleId(Integer roleId) {
        return usersRepository.findByRoleId(roleId);
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User findByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    @Override
    public Map<String, Integer> getUserSummaryDetaialsForSales(Integer userId) {

         int numberOfBidsRecievedThisMonth = userInterfaceDao.getTotalNumberOfBidsRecievedThisMonth(userId);
         int numberOfBidsYetToUpdateThisMonth = userInterfaceDao.getTotalNumberOfBidsYetToUpdateThisMonth(userId, "");
         int numberOfProjectsAllotedThisMonth = userInterfaceDao.getTotalNumberOfProjectsAllotedThisMonth(userId, "Allotted");

         int numberOfBidsRecievedThisYear = userInterfaceDao.getTotalNumberOfBidsRecievedThisYear(userId);
         int newClientsAchievedThisYear = userInterfaceDao.getTotalNewClientsAchievedThisYear(userId);
         int numberOfProjectsAllotedThisYear = userInterfaceDao.getTotalNumberOfProjectsAllotedThisYear(userId, "Allotted");

         int monthlyTarget = salesTargetDao.getMonthlyTarget(userId);
         int monthlyTargetAchieved = userInterfaceDao.getmonthlyTargetAchieved(userId, "Allotted");
         int monthyTargetYetToAchieve = monthlyTarget - monthlyTargetAchieved;

         int yearlyTarget = salesTargetDao.getYearlyTarget(userId);
         int yearlyTargetAchieved = userInterfaceDao.yearlyTargetAchieved(userId, "Allotted");
         int yearlyYetToAchieveTargets = yearlyTarget - yearlyTargetAchieved;

        Map<String, Integer> summary = new HashMap<>();
        summary.put("numberOfBidsRecievedThisMonth", numberOfBidsRecievedThisMonth);
        summary.put("numberOfBidsYetToUpdateThisMonth", numberOfBidsYetToUpdateThisMonth);
        summary.put("numberOfProjectsAllotedThisMonth", numberOfProjectsAllotedThisMonth);

        summary.put("numberOfBidsRecievedThisYear", numberOfBidsRecievedThisYear);
        summary.put("newClientsAchievedThisYear", newClientsAchievedThisYear);
        summary.put("numberOfProjectsAllotedThisYear", numberOfProjectsAllotedThisYear);

        summary.put("monthlyTarget", monthlyTarget);
        summary.put("monthlyTargetAchieved", monthlyTargetAchieved);
        summary.put("monthyTargetYetToAchieve", monthyTargetYetToAchieve);

        summary.put("yearlyTarget", yearlyTarget);
        summary.put("yearlyTargetAchieved", yearlyTargetAchieved);
        summary.put("yearlyYetToAchieveTargets", yearlyYetToAchieveTargets);

        return summary;
    }

    @Override
    public List<String> getUserNotifications(Integer userId) {
        List<Quotation> quotationList = userInterfaceDao.getAllNotificationsToTheUser(userId);
        List<String> notifications = new ArrayList<>();

        for (Quotation q : quotationList) {
            if (q.getProjectName() != null && q.getQuotationDueDate() != null) {
                String message = String.format(
                        "Hi, the project '%s' has a due date on %s. Please look into this.",
                        q.getProjectName(),
                        q.getQuotationDueDate().toString()
                );
                notifications.add(message);
            }
        }
        return notifications;
    }

    @Override
    public void updateQuotationStatus(String status, Integer quotationId) {
        quotationRepository.updateQuotationStatus(status, quotationId);
    }

    public List<User> getUserReportees(Integer supUserId) {
        List<User> directReports = usersRepository.findBySupUserId(supUserId);
        return directReports;
    }
}
