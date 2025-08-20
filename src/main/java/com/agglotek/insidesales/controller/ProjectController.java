package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.dao.entity.Project;
import com.agglotek.insidesales.dto.ProjectInfoDTO;
import com.agglotek.insidesales.service.api.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(ApiConstants.PROJECT_DETAILS)
    public ResponseEntity<List<ProjectInfoDTO>> getProjectDetailsBySalesPerson(@PathVariable Integer salesPersonId) {
        List<ProjectInfoDTO> projectDetails = projectService.getProjectDetailsBySalesPersonId(salesPersonId);
        return ResponseEntity.ok(projectDetails);
    }

    @PutMapping(ApiConstants.UPDATE_PO_NUM)
    public ResponseEntity<ApiResponse> updateClientProjectNumber(
            @RequestBody ProjectInfoDTO request) {

        boolean success = projectService.updateClientProjectNumber(
                request.getProjectId(), request.getClientProjectNumber());

        if (success) {
            return ResponseEntity.ok(new ApiResponse(true, "Client Project Number updated successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Project not found!"));
        }
    }
}
