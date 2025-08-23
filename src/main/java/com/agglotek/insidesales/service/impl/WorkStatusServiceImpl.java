package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.WorkStatus;
import com.agglotek.insidesales.repository.WorkStatusRepository;
import com.agglotek.insidesales.service.api.IWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkStatusServiceImpl implements IWorkStatusService {

    @Autowired
    private WorkStatusRepository repository;

    public WorkStatus addEntry(WorkStatus request, Integer userId) {
        WorkStatus entry = new WorkStatus();
        entry.setUserId(userId);
        entry.setDate(request.getDate());
        entry.setNoOfCalls(request.getNoOfCalls() != null ? request.getNoOfCalls() : 0);
        entry.setSamplesSend(request.getSamplesSend() != null ? request.getSamplesSend() : 0);
        entry.setBidsReceived(request.getBidsReceived() != null ? request.getBidsReceived() : 0);
        entry.setProjectsReceived(request.getProjectsReceived() != null ? request.getProjectsReceived() : 0);

        return repository.save(entry);
    }

    @Override
    public List<WorkStatus> getWorkStatusByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }
}
