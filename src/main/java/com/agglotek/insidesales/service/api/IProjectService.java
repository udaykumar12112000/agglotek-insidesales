package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.ProjectInfoDTO;
import com.agglotek.insidesales.dto.ProjectQuotationDTO;

import java.util.List;


public interface IProjectService {
    public List<ProjectInfoDTO> getProjectDetailsBySalesPersonId(Integer salesPersonId) ;

    public boolean updateProjectDetails(ProjectInfoDTO request);

    public void createProjectFromQuotation(Quotation quotation);

    List<Project> getProjectsByManagerId(Integer managerId);

    List<ProjectQuotationDTO> getProjectsWithQuotationsByManagerId(Integer managerId);

    boolean updateProject(Project updateRequest);

    List<Project> getProjectsByStatuses(List<String> projectStatuses);
}
