package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.constants.AppConstants;
import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.dto.ProjectDetailsWithQuotationDetails;
import com.agglotek.insidesales.dto.ProjectInfoDTO;
import com.agglotek.insidesales.dto.ProjectQuotationDTO;
import com.agglotek.insidesales.dto.ProjectStatusFilterDTO;
import com.agglotek.insidesales.service.api.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
 @CrossOrigin(
         origins = "http://localhost:4200",
         allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"},
         methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS},
         allowCredentials = "false" // set true only if you use cookies
 )
/** use for local **/
//@CrossOrigin(
//       origins = {"http://localhost:4200"},
//       allowedHeaders = "*"
//)

@RequestMapping(ApiConstants.MANAGER_DATA)
public class ProjectManagerController {

    @Autowired
    private IProjectService projectService;

    @GetMapping(ApiConstants.GET_PROJECTS_FOR_PROJECT_MANAGER)
    public ResponseEntity<ApiResponse> getProjectsForProjectManager(@RequestHeader("User-Id") Integer managerId) {

        if (managerId == null) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse(false, "managerId must not be empty"));
        }

        List<ProjectQuotationDTO> projectsWithQuotations = projectService.getProjectsWithQuotationsByManagerId(managerId);

        return ResponseEntity.ok(new ApiResponse(true, "Fetched projects with quotation details successfully", projectsWithQuotations));
    }

    @PutMapping(ApiConstants.UPDATE_PROJECT_DETAILS)
    public ResponseEntity<ApiResponse> updateProject(@RequestBody Project updateRequest) {
        try {
            boolean updated = projectService.updateProject(updateRequest);

            if (updated) {
                return ResponseEntity.ok(new ApiResponse(true, "Project updated successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(new ApiResponse(false, "Project not found for projectId: " + updateRequest.getProjectId()));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse(false, "Internal server error"));
        }
    }

    @PostMapping(ApiConstants.FILTER_BY_STATUS)
    public ResponseEntity<ApiResponse> getProjectsByStatuses(@RequestBody ProjectStatusFilterDTO request) {

        if (request.getProjectStatuses() == null || request.getProjectStatuses().isEmpty()) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse(false, "Project statuses must not be empty"));
        }

        List<ProjectDetailsWithQuotationDetails> projects = projectService.getProjectsByStatuses(request.getProjectStatuses());

        return ResponseEntity.ok(new ApiResponse(true, "Fetched projects by status successfully", projects));
    }
}
