package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dto.ProjectInfoDTO;

import java.util.List;


public interface IProjectService {
    public List<ProjectInfoDTO> getProjectDetailsBySalesPersonId(Integer salesPersonId) ;

    public boolean updateClientProjectNumber(Integer projectId, String clientProjectNumber);
}
