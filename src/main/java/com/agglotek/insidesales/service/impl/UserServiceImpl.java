package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.dao.api.ISalesTargetDao;
import com.agglotek.insidesales.dao.api.IUserInterfaceDao;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.dto.NotificationDTO;
import com.agglotek.insidesales.repository.QuotationRepository;
import com.agglotek.insidesales.repository.UserRepository;
import com.agglotek.insidesales.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    public List<NotificationDTO> getUserNotifications(Integer userId) {
        List<Quotation> quotationList = userInterfaceDao.getAllNotificationsToTheUser(userId);
        List<NotificationDTO> notifications = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (Quotation q : quotationList) {
            LocalDate dueDate = q.getQuotationDueDate();
            String project = q.getProjectName();

            if (project != null && dueDate != null) {
                long daysDiff = ChronoUnit.DAYS.between(today, dueDate);
                String message;
                String priority;

                if (daysDiff < 0) {
                    // Overdue
                    message = String.format("Project %s from Fabricator Bid status update is overdue by %d days.", project, Math.abs(daysDiff));
                    priority = "HIGH";
                } else if (daysDiff <= 2) {
                    message = String.format("Project %s from Fabricator Bid status update in %d day(s).", project, daysDiff);
                    priority = "HIGH";
                } else if (daysDiff <= 4) {
                    message = String.format("Project %s from Fabricator Bid status update in %d day(s).", project, daysDiff);
                    priority = "MEDIUM";
                } else {
                    message = String.format("Project %s from Fabricator Bid status update in %d day(s).", project, daysDiff);
                    priority = "LOW";
                }

                notifications.add(new NotificationDTO(message, priority));
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

    @Override
    public Map<String, Integer> getCumulativeSalesSummary(List<Integer> salesPersonIds) {
        Map<String, Integer> cumulativeSummary = new HashMap<>();

        for (Integer salesPersonId : salesPersonIds) {
            Map<String, Integer> individualSummary = getUserSummaryDetaialsForSales(salesPersonId);

            // Add values to cumulative map
            individualSummary.forEach((key, value) ->
                    cumulativeSummary.merge(key, value, Integer::sum));
        }

        return cumulativeSummary;
    }

    @Override
    public ApiResponse changePassword(Integer userId, Map<String, String> request) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return new ApiResponse(false, "User not found");
        }

        User user = optionalUser.get();

        // Validate old password
        if (!user.getPassword().equals(request.get("oldPassword"))) {
            return new ApiResponse(false, "Old password is incorrect");
        }

        if(user.getPassword().equals(request.get("newPassword"))){
            return new ApiResponse(false, "Old password and new password cannot be same");
        }

        user.setPassword(request.get("newPassword"));
        usersRepository.save(user);

        return new ApiResponse(true, "Password changed successfully");
    }

}
