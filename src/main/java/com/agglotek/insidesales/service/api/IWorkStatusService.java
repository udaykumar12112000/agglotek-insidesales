package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.WorkStatus;

import java.util.List;

public interface IWorkStatusService {

    public WorkStatus addEntry(WorkStatus request);

    public List<WorkStatus> getWorkStatusByUserId(Integer userId);
}
