package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.AppConstants;
import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.dao.entity.WorkStatus;
import com.agglotek.insidesales.repository.WorkStatusRepository;
import com.agglotek.insidesales.service.api.IRoleService;
import com.agglotek.insidesales.service.api.IUserService;
import com.agglotek.insidesales.service.api.IWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class WorkStatusServiceImpl implements IWorkStatusService {

    @Autowired
    private WorkStatusRepository repository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    public WorkStatus addEntry(WorkStatus request, Integer userId) {
        WorkStatus entry = new WorkStatus();
        entry.setUserId(userId);
        entry.setDate(request.getDate());
        entry.setNoOfCalls(request.getNoOfCalls() != null ? request.getNoOfCalls() : 0);
        entry.setSamplesSend(request.getSamplesSend() != null ? request.getSamplesSend() : 0);
        entry.setBidsReceived(request.getBidsReceived() != null ? request.getBidsReceived() : 0);
        entry.setProjectsReceived(request.getProjectsReceived() != null ? request.getProjectsReceived() : 0);
        entry.setRemarks(request.getRemarks());

        return repository.save(entry);
    }

    @Override
    public List<WorkStatus> getWorkStatusByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }

    public ApiResponse editWorkStatus(WorkStatus request, Integer userId) {

        List<User> userList = userService.getUserByUserId(userId);
        if(userList.isEmpty() || userList==null)
            return new ApiResponse(false, "User not Found!");


        String editorRoleName = roleService.getRoleNameById(userList.get(0).getRoleId());
        if(editorRoleName.equals(AppConstants.ADMIN)) {
            Optional<WorkStatus> statusOpt = repository.findById(request.getWorkStatusId());

            if (!statusOpt.isPresent()) {
                return new ApiResponse(false, "Work status not found!");
            }

            WorkStatus workStatus = statusOpt.get();

            if (request.getDate() != null)
                workStatus.setDate(request.getDate());

            if (request.getNoOfCalls() != null)
                workStatus.setNoOfCalls(request.getNoOfCalls());

            if (request.getSamplesSend() != null)
                workStatus.setSamplesSend(request.getSamplesSend());

            if (request.getBidsReceived() != null)
                workStatus.setBidsReceived(request.getBidsReceived());

            if (request.getProjectsReceived() != null)
                workStatus.setProjectsReceived(request.getProjectsReceived());

            if (request.getRemarks() != null)
                workStatus.setRemarks(request.getRemarks());

            repository.save(workStatus);
            return new ApiResponse(true, "Work status updated successfully!", workStatus);
        }
        return new ApiResponse(false, "You don't have permission to edit the work status!");
    }

    @Override
    public List<WorkStatus> getWorkStatusByUserAndDateRange(Integer userId, String startDate, String endDate) {


        // ISO format: yyyy-MM-dd
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);


        List<WorkStatus> list = repository.findByUserIdAndDateBetween(userId, start, end);


        // set month & year from date
        list.forEach(ws -> {ws.setMonth(ws.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));ws.setYear(String.valueOf(ws.getDate().getYear()));});


        return list;
    }
}
