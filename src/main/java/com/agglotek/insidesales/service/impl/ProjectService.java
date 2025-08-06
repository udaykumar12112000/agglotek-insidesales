package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dto.ProjectInfoDTO;
import com.agglotek.insidesales.repository.ProjectRepository;
import com.agglotek.insidesales.service.api.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    public List<ProjectInfoDTO> getProjectDetailsBySalesPersonId(Integer salesPersonId) {
        List<Object[]> rawResults = projectRepository.findProjectDetailsBySalesPersonId(salesPersonId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<ProjectInfoDTO> projects = rawResults.stream().map(row -> {
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
                    (String) row[10]                      // comments
            );
        }).collect(Collectors.toList());
        return projects;
    }

    public boolean updateClientProjectNumber(Integer projectId, String clientProjectNumber) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setClientProjectNumber(clientProjectNumber);
            projectRepository.save(project);
            return true;
        }
        return false;
    }

}
