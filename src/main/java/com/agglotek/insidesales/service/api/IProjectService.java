package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.ProjectInfoDTO;

import java.util.List;


public interface IProjectService {
    public List<ProjectInfoDTO> getProjectDetailsBySalesPersonId(Integer salesPersonId) ;

    public boolean updateProjectDetails(ProjectInfoDTO request);

    public void createProjectFromQuotation(Quotation quotation);
}
