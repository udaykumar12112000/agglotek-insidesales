package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.dao.entity.WorkStatus;
import com.agglotek.insidesales.service.api.IWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(
//        origins = "http://localhost:4200",
//        allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"},
//        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS},
//        allowCredentials = "false" // set true only if you use cookies
//)
@CrossOrigin(
        origins = {"http://localhost:4200"},
        allowedHeaders = "*"
)
@RequestMapping(ApiConstants.WORK_STATUS_APIS)
public class WorkStatusController {

    @Autowired
    private IWorkStatusService service;

    @PostMapping(ApiConstants.ADD_WORK_STATUS)
    public ResponseEntity<?> addWorkStatus(@RequestHeader("User-Id") Integer userId, @RequestBody WorkStatus request) {
        try {
            WorkStatus saved = service.addEntry(request, userId);
            return ResponseEntity.ok().body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save work status entry!");
        }
    }

    @GetMapping(ApiConstants.GET_WORK_STATUS)
    public ResponseEntity<List<WorkStatus>> getEntriesByUserId(@RequestHeader("User-Id") Integer userId) {
        List<WorkStatus> entries = service.getWorkStatusByUserId(userId);
        return ResponseEntity.ok(entries);
    }

    @PutMapping(ApiConstants.EDIT_WORK_STATUS)
    public ResponseEntity<ApiResponse> editWorkStatus(@RequestHeader("User-Id") Integer userId, @RequestBody WorkStatus request) {

        ApiResponse response = service.editWorkStatus(request, userId);
        return ResponseEntity.ok(response);

    }
}
