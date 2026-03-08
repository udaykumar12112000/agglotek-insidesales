package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.ProjectDetailsWithQuotationDetails;
import com.agglotek.insidesales.dto.ProjectInfoDTO;
import com.agglotek.insidesales.dto.ProjectQuotationDTO;
import com.agglotek.insidesales.repository.ProjectRepository;
import com.agglotek.insidesales.repository.QuotationRepository;
import com.agglotek.insidesales.repository.UserRepository;
import com.agglotek.insidesales.service.api.IConnectionEngService;
import com.agglotek.insidesales.service.api.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IConnectionEngService connectionEngService;

    public List<ProjectInfoDTO> getProjectDetailsBySalesPersonId(Integer salesPersonId, Boolean isConnEng) {
        List<Object[]> rawResults;

        // Check if the user is Admin
        boolean isAdmin = userRepository.isAdminUser(salesPersonId);

        if (isAdmin) {
            rawResults = projectRepository.findAllProjectDetails();
        } else {
            rawResults = projectRepository.findProjectDetailsBySalesPersonId(salesPersonId);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<ProjectInfoDTO> filteredProjects = new ArrayList<>();

        for (Object[] row : rawResults) {
            Integer quotationId = (Integer) row[1];
            Quotation quotation = quotationRepository.getByQuotationId(quotationId);
            Boolean isConnEngInDb = quotation.getConnectionEngineering();

            ProjectInfoDTO dto = new ProjectInfoDTO(
                    (String) row[0],
                    quotationId,
                    (Integer) row[2],
                    (String) row[3],
                    ((Date) row[4]).toLocalDate().format(formatter),
                    (String) row[5],
                    (Integer) row[6],
                    (String) row[7],
                    (String) row[8],
                    (BigDecimal) row[9],
                    (String) row[10],
                    (String) row[11],
                    (BigDecimal) row[12],
                    (String) row[13],
                    (Integer) row[14],
                    (String) row[15],
                    row[16] != null ? ((Date) row[16]).toLocalDate() : null,
                    row[17] != null ? ((Date) row[17]).toLocalDate() : null
            );

            if (isConnEng == null) {
                // Case 1: Parameter not passed → include all
                filteredProjects.add(dto);
            } else if (Boolean.TRUE.equals(isConnEng) && isConnEngInDb) {
                // Case 2: Only connection engineering projects
                Integer connEngId = quotation.getConnectionEngId();
                if (connEngId != null) {
                    connectionEngService.getById(Long.valueOf(connEngId)).ifPresent(connEng -> {
                        dto.setConnectionEng(connEng);
                    });
                }
                filteredProjects.add(dto);
            } else if (Boolean.FALSE.equals(isConnEng) && !isConnEngInDb) {
                // Case 3: Only non-connection engineering projects
                filteredProjects.add(dto);
            }
        }

        return filteredProjects;


//        return rawResults.stream().map(row -> new ProjectInfoDTO(
//                (String) row[0],
//                (Integer) row[1],
//                (Integer) row[2],
//                (String) row[3],
//                ((Date) row[4]).toLocalDate().format(formatter),
//                (String) row[5],
//                (Integer) row[6],
//                (String) row[7],
//                (String) row[8],
//                (BigDecimal) row[9],
//                (String) row[10],
//                (String) row[11],
//                (BigDecimal) row[12],
//                (String) row[13],
//                (Integer) row[14],
//                (String) row[15],
//                row[16] != null ? ((Date) row[16]).toLocalDate() : null,
//                row[17] != null ? ((Date) row[17]).toLocalDate() : null
//        )).collect(Collectors.toList());
    }
    private boolean checkIsConnEng(Integer quotationId) {

        System.out.println("JAXX :: quotationId : "+quotationId);
        Quotation quotation = quotationRepository.getByQuotationId(quotationId);
        Boolean connEngFlag = quotation.getConnectionEngineering();
        return connEngFlag;
    }


    public boolean updateProjectDetails(ProjectInfoDTO request) {
        Optional<Project> optionalProject = projectRepository.findById(request.getProjectId());
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            if (request.getComments() != null)
                project.setComments(request.getComments());

            if (request.getIfaDate() != null)
                project.setIfaDate(request.getIfaDate());

            if (request.getUpdatedTime() != null)
                project.setUpdatedTime(LocalDateTime.now());

            if (request.getProjectManagerId() != null)
                project.setProjectManagerId(request.getProjectManagerId());

            if (request.getProjectStatus() != null)
                project.setProjectStatus(request.getProjectStatus());

            if (request.getPlannedSubmittedDate() != null)
                project.setPlannedSubmittedDate(request.getPlannedSubmittedDate());

            if (request.getIfcSubmissionDate() != null)
                project.setIfcSubmissionDate(request.getIfcSubmissionDate());

            if (request.getIfaSubmissionDate() != null)
                project.setIfaSubmissionDate(request.getIfaSubmissionDate());

            if (request.getClientProjectNumber() != null)
                project.setClientProjectNumber(request.getClientProjectNumber());

            if (request.getPurchaseOrder() != null)
                project.setPurchaseOrder(request.getPurchaseOrder());

            if (request.getConnPO() != null)
                project.setConnPO(request.getConnPO());

            if (request.getProjectValue() != null)
                project.setProjectValue(request.getProjectValue());

            if(request.getProjectManagerId() != null)
                project.setProjectManagerId(request.getProjectManagerId());

            if(request.getProjectStatus() != null){
                project.setProjectStatus(request.getProjectStatus());
            }

            projectRepository.save(project);
            return true;
        }
        return false;
    }

    public void createProjectFromQuotation(Quotation quotation) {
        Project project = new Project();
        project.setQuotationId(quotation.getQuotationId());

        // Generate project number
        String projectNumber = generateProjectNumber();
        project.setProjectNumber(projectNumber);
        project.setBalanceAmt(quotation.getQuotationValue());
        project.setProjectValue(quotation.getQuotationValue());
        project.setCreatedTime(LocalDateTime.now());
        projectRepository.save(project);
    }

    private String generateProjectNumber() {
        String yearSuffix = String.valueOf(Year.now().getValue()).substring(2); // "25"

        // Find last project for this year
        String prefix = "PO" + yearSuffix + "-";

        String lastProjectNumber = projectRepository.findLastProjectNumberForYear(prefix);

        int nextSeq = 1;
        if (lastProjectNumber != null) {
            String lastSeqStr = lastProjectNumber.substring(lastProjectNumber.lastIndexOf("-") + 1);
            nextSeq = Integer.parseInt(lastSeqStr) + 1;
        }

        return String.format("%s%03d", prefix, nextSeq); // PO25-001
    }

    @Override
    public List<Project> getProjectsByManagerId(Integer managerId) {
        return projectRepository.findByProjectManagerId(managerId);
    }

    @Override
    public List<ProjectQuotationDTO> getProjectsWithQuotationsByManagerId(Integer managerId) {

        List<Project> projects = projectRepository.findByProjectManagerId(managerId);

        List<ProjectQuotationDTO> result = new ArrayList<>();

        for (Project project : projects) {
            Quotation quotation = null;

            if (project.getQuotationId() != null) {
                quotation = quotationRepository.findById(project.getQuotationId()).orElse(null);
            }

            result.add(new ProjectQuotationDTO(project, quotation));
        }

        return result;
    }

    @Override
    public boolean updateProject(Project updateRequest) {
        if (updateRequest.getProjectId() == null) {
            throw new IllegalArgumentException("projectId is required");
        }

        Optional<Project> optionalProject = projectRepository.findById(updateRequest.getProjectId());

        if (optionalProject.isEmpty()) {
            return false;  // Project not found
        }

        Project project = optionalProject.get();

        // Apply non-null fields from updateRequest
        if (updateRequest.getQuotationId() != null) {
            project.setQuotationId(updateRequest.getQuotationId());
        }
        if (updateRequest.getComments() != null) {
            project.setComments(updateRequest.getComments());
        }
        if (updateRequest.getIfaDate() != null) {
            project.setIfaDate(updateRequest.getIfaDate());
        }
        if (updateRequest.getProjectManagerId() != null) {
            project.setProjectManagerId(updateRequest.getProjectManagerId());
        }
        if (updateRequest.getProjectStatus() != null) {
            project.setProjectStatus(updateRequest.getProjectStatus());
        }
        if (updateRequest.getPlannedSubmittedDate() != null) {
            project.setPlannedSubmittedDate(updateRequest.getPlannedSubmittedDate());
        }
        if (updateRequest.getIfcSubmissionDate() != null) {
            project.setIfcSubmissionDate(updateRequest.getIfcSubmissionDate());
        }
        if (updateRequest.getIfaSubmissionDate() != null) {
            project.setIfaSubmissionDate(updateRequest.getIfaSubmissionDate());
        }
        if (updateRequest.getClientProjectNumber() != null) {
            project.setClientProjectNumber(updateRequest.getClientProjectNumber());
        }
        if (updateRequest.getProjectNumber() != null) {
            project.setProjectNumber(updateRequest.getProjectNumber());
        }
        if (updateRequest.getPurchaseOrder() != null) {
            project.setPurchaseOrder(updateRequest.getPurchaseOrder());
        }
        if (updateRequest.getConnPO() != null) {
            project.setConnPO(updateRequest.getConnPO());
        }

        project.setUpdatedTime(LocalDateTime.now());

        projectRepository.save(project);

        return true;
    }

    @Override
    public List<ProjectDetailsWithQuotationDetails> getProjectsByStatuses(List<String> projectStatuses) {
        if (projectStatuses == null || projectStatuses.isEmpty()) {
            return Collections.emptyList();
        }
        return projectRepository.findProjectsWithNameByStatus(projectStatuses);
    }

}
