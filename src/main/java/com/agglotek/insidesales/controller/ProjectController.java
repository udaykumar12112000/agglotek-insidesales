package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dto.ProjectInfoDTO;
import com.agglotek.insidesales.dto.QuotationInfoDTO;
import com.agglotek.insidesales.service.api.IFileService;
import com.agglotek.insidesales.service.api.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(
        origins = {"http://localhost:4200"},
        allowedHeaders = "*"
)
@RequestMapping(ApiConstants.PROJECT_APIS)
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IFileService fileService;

    @GetMapping(ApiConstants.PROJECT_DETAILS)
    public ResponseEntity<List<ProjectInfoDTO>> getProjectDetailsBySalesPerson(@RequestHeader("User-Id") Integer salesPersonId) throws IOException {
        List<ProjectInfoDTO> projectDetails = projectService.getProjectDetailsBySalesPersonId(salesPersonId);
        for(ProjectInfoDTO project : projectDetails) {
            project.setFilesList(fileService.listFiles(project.getProjectNumber(), project.getProjectName(), project.getClientId()));
        }
        return ResponseEntity.ok(projectDetails);
    }

    @PutMapping(ApiConstants.UPDATE_PROJECT)
    public ResponseEntity<ApiResponse> updateProjectDetails(
            @RequestBody ProjectInfoDTO request) {

        boolean success = projectService.updateProjectDetails(request);

        if (success) {
            return ResponseEntity.ok(new ApiResponse(true, "Project Details updated successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Project not found!"));
        }
    }
}
