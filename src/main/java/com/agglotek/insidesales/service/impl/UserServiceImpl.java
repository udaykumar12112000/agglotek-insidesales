package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.AppConstants;
import com.agglotek.insidesales.dao.api.ISalesTargetDao;
import com.agglotek.insidesales.dao.api.IUserInterfaceDao;
import com.agglotek.insidesales.dao.entity.Client;
import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.dto.NotificationDTO;
import com.agglotek.insidesales.repository.ProjectRepository;
import com.agglotek.insidesales.repository.PurchaseOrderRepository;
import com.agglotek.insidesales.repository.QuotationRepository;
import com.agglotek.insidesales.repository.UserRepository;
import com.agglotek.insidesales.service.api.IClientService;
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

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private IClientService clientService;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

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

        Integer numberOfBidsRecievedThisMonth = userInterfaceDao.getTotalNumberOfBidsRecievedThisMonth(userId);
        Integer numberOfBidsYetToUpdateThisMonth = userInterfaceDao.getTotalNumberOfBidsYetToUpdateThisMonth(userId, "");
        Integer numberOfProjectsAllotedThisMonth = userInterfaceDao.getTotalNumberOfProjectsAllotedThisMonth(userId, AppConstants.ALLOTTED);

        Integer numberOfBidsRecievedThisYear = userInterfaceDao.getTotalNumberOfBidsRecievedThisYear(userId);
        Integer newClientsAchievedThisYear = userInterfaceDao.getTotalNewClientsAchievedThisYear(userId);
        Integer numberOfProjectsAllotedThisYear = userInterfaceDao.getTotalNumberOfProjectsAllotedThisYear(userId, AppConstants.ALLOTTED);

        Integer monthlyTarget = salesTargetDao.getMonthlyTarget(userId);
        Integer monthlyTargetAchieved = userInterfaceDao.getmonthlyTargetAchieved(userId, AppConstants.ALLOTTED);
        Integer monthyTargetYetToAchieve = (monthlyTarget!=null && monthlyTargetAchieved!=null)? monthlyTarget - monthlyTargetAchieved: 0;

        Integer yearlyTarget = salesTargetDao.getYearlyTarget(userId);
        Integer yearlyTargetAchieved = userInterfaceDao.yearlyTargetAchieved(userId, AppConstants.ALLOTTED);
        Integer yearlyYetToAchieveTargets = (yearlyTarget!=null && yearlyTargetAchieved!=null)? yearlyTarget - yearlyTargetAchieved : 0;

        Map<String, Integer> summary = new HashMap<>();
        summary.put("numberOfBidsRecievedThisMonth", numberOfBidsRecievedThisMonth!=null? numberOfBidsRecievedThisMonth : 0);
        summary.put("numberOfBidsYetToUpdateThisMonth", numberOfBidsYetToUpdateThisMonth!=null? numberOfBidsYetToUpdateThisMonth : 0);
        summary.put("numberOfProjectsAllotedThisMonth", numberOfProjectsAllotedThisMonth!=null? numberOfProjectsAllotedThisMonth : 0);

        summary.put("numberOfBidsRecievedThisYear", numberOfBidsRecievedThisYear!=null? numberOfBidsRecievedThisYear : 0);
        summary.put("newClientsAchievedThisYear", newClientsAchievedThisYear!=null? newClientsAchievedThisYear : 0);
        summary.put("numberOfProjectsAllotedThisYear", numberOfProjectsAllotedThisYear!=null? numberOfProjectsAllotedThisYear : 0);

        summary.put("monthlyTarget", monthlyTarget!=null? monthlyTarget : 0);
        summary.put("monthlyTargetAchieved", monthlyTargetAchieved!=null? monthlyTargetAchieved : 0);
        summary.put("monthyTargetYetToAchieve", monthyTargetYetToAchieve);

        summary.put("yearlyTarget", yearlyTarget!=null? yearlyTarget : 0);
        summary.put("yearlyTargetAchieved", yearlyTargetAchieved!=null? yearlyTargetAchieved : 0);
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

    @Override
    public String getAcctMail() {
        List<String> emails = usersRepository.getAcctMail();
        return String.join(",", emails);  // join into single string
    }

    @Override
    public List<?> getAllUsersWithRole() {
        return usersRepository.findAllUsersWithRole();
    }

    public List<NotificationDTO> notifyAdminsForUnassignedProjects() {
        List<Project> projects = projectRepository.findAllAllottedProjectsWithoutManager();

        if (projects.isEmpty()) {
            return List.of(new NotificationDTO("No new allotted projects without manager.", null));
        }
        // Find all admins
        // List<User> admins = usersRepository.findAllAdmins();

        List<NotificationDTO> notifications = new ArrayList<>();
        for (Project project : projects) {
            Quotation quotation = quotationRepository.getByQuotationId(project.getQuotationId());
            Client client = clientService.getClientByProjectId(project.getProjectId());
            if (quotation == null) {
                continue;
            }

            // Case 1: Allotted project without manager
            if (project.getProjectManagerId() == null) {
                String message = String.format(
                        "Project : %s - %s from Fabricator %s is allotted, assign manager.",
                        quotation.getProjectName(),
                        project.getProjectNumber(),
                        client.getName()
                );
                notifications.add(new NotificationDTO(message, "HIGH"));
            }

            // Case 2: Connection engineering required
            if (Boolean.TRUE.equals(quotation.getConnectionEngineering())) {
                // Check if Purchase Order already exists for this project
                boolean poExists = purchaseOrderRepository.existsByProjectId(project.getProjectId());

                if (!poExists) {
                    String message = String.format(
                            "Project : %s - %s from Fabricator %s has Connection Engineering, Generate Purchase Order.",
                            quotation.getProjectName(),
                            project.getProjectNumber(),
                            client.getName()
                    );
                    notifications.add(new NotificationDTO(message, "HIGH"));
                }
            }
        }
        return notifications;
    }

}
