package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dao.entity.Quotation;
import com.agglotek.insidesales.dto.ProjectInfoDTO;
import com.agglotek.insidesales.repository.ProjectRepository;
import com.agglotek.insidesales.service.api.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    public List<ProjectInfoDTO> getProjectDetailsBySalesPersonId(Integer salesPersonId) {
        List<Object[]> rawResults = projectRepository.findProjectDetailsBySalesPersonId(salesPersonId);
        System.out.println("JAXX : rawResults : "+rawResults);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<ProjectInfoDTO> projects = rawResults.stream().map(row -> {
            System.out.println("JAXX : row : "+row[0]);
            System.out.println("JAXX : row : "+(String) row[0]);
                    System.out.println("JAXX : row : "+(Integer) row[1]);
                    System.out.println("JAXX : row : "+(String)row[2]);
                    System.out.println("JAXX : row : "+((Date) row[3]).toLocalDate().format(formatter));
                    System.out.println("JAXX : row : "+(String) row[4]);
            System.out.println("JAXX : row : "+(Integer) row[5]);                    // clientId
            System.out.println("JAXX : row : "+(String) row[6]);                     // projectName
            System.out.println("JAXX : row : "+(String) row[7]);                     // country
            System.out.println("JAXX : row : "+(BigDecimal) row[8]);                 // projectValue
            System.out.println("JAXX : row : "+(String) row[9]);                     // clientProjectNumber
            System.out.println("JAXX : row : "+(String) row[10]);                     // purchaseOrder
            System.out.println("JAXX : row : "+(String) row[11]);                     // connPO
            System.out.println("JAXX : row : "+(String) row[12]);                      // comments
            return new ProjectInfoDTO(
                    (String) row[0],                     // quotationNumber
                    (Integer) row[1],                     // projectId
                    (String) row[2],                     // projectNumber
                    ((Date) row[3]).toLocalDate().format(formatter), // formatted date string
                    (String) row[4],                     // clientName
                    (Integer) row[5],                     // clientId
                    (String) row[6],                     // projectName
                    (String) row[7],                     // country
                    (BigDecimal) row[8],                 // projectValue
                    (String) row[9],                     // clientProjectNumber
                    (String) row[10],                     // purchaseOrder
                    (BigDecimal) row[11],                     // connPO
                    (String) row[12]                      // comments
            );
        }).collect(Collectors.toList());
        return projects;
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



}
