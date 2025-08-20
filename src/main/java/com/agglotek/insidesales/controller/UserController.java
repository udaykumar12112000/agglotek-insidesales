package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.constants.AppConstants;
import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.service.api.IRoleService;
import com.agglotek.insidesales.service.api.IUserService;
import com.agglotek.insidesales.service.impl.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(
        origins = {"http://localhost:4200"},
        allowedHeaders = "*"
)
@RequestMapping(ApiConstants.USER_APIS)
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping(ApiConstants.CREATE_USER)
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {

        if (user.getName() == null || user.getName().isEmpty())
            return ResponseEntity.status(500).body(new ApiResponse(false, "User name must not be empty"));

        if (!user.getEditorRoleName().equals(AppConstants.ADMIN))
            return ResponseEntity.ok(new ApiResponse(false, "User don't have permission"));
        user.setPassword(AppConstants.DEFAULT_PASSWORD);
        userService.addUser(user);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return ResponseEntity.ok(new ApiResponse(true, "Created user successfully", user));

    }

    @GetMapping(ApiConstants.GET_USER)
    public List<User> getUsers(@RequestParam(required = false) Integer userId,
                               @RequestParam(required = false) Integer roleId) {

        List<User> users;
        if (userId != null) {
            users = userService.getUserByUserId(userId);
        } else if (roleId != null) {
            users = userService.getUsersByRoleId(roleId);
        } else {
            users = userService.getAllUsers();
        }
        users.forEach(user -> {
                String hashedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(hashedPassword);
        });

        return users;
    }

    @PostMapping(ApiConstants.EDIT_USER)
    public ResponseEntity<ApiResponse> editUser(@RequestBody User request) {
        List<User> users = userService.getUserByUserId(request.getUserId());
        if (users.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(false, "User not Found"));
        }
        User user = users.get(0);

        if (!AppConstants.ADMIN.equalsIgnoreCase(request.getEditorRoleName())) {
            if (request.getDepartment() != null || request.getSupUserId() != null || request.getRoleId() != null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "User doesn't have permission to update department, supervisor or role"));
            }
        }

        if (request.getName() != null) user.setName(request.getName());
        if (request.getDesignation() != null) user.setDesignation(request.getDesignation());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) user.setPhoneNumber(request.getPhoneNumber());

        if (AppConstants.ADMIN.equalsIgnoreCase(user.getEditorRoleName())) {
            if (request.getDepartment() != null) user.setDepartment(request.getDepartment());
            if (request.getSupUserId() != null) user.setSupUserId(request.getSupUserId());
            if (request.getRoleId() != null) user.setRoleId(request.getRoleId());
        }

        userService.addUser(user);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return ResponseEntity.ok(new ApiResponse(true, "Edit user successful", user));
    }

    @PostMapping(ApiConstants.USER_LOGIN)
    public ResponseEntity<ApiResponse> login(@RequestBody User request) {
        User user = userService.findByEmail(request.getEmail());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid email or password"));
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Password doesn't match"));
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return ResponseEntity.ok(new ApiResponse(true, "Login successful", user));
    }

    //@GetMapping(ApiConstants.GET_USER_SUMMARY)
    public ResponseEntity<ApiResponse> getUserSummaryDetails(@RequestParam(required = true) Integer userId,
                                                      @RequestParam(required = true) Integer roleId) {

        if (userId == null || roleId == null) {
            //log.error("User Id and role Id must not be null");
        } else if (roleId == 2) {
            Map<String, Integer> summaryDetails = userService.getUserSummaryDetaialsForSales(userId);
            return ResponseEntity.ok(new ApiResponse(true, "Summary Details Fetching successful", summaryDetails));
//        } else if (roleId == 3) {
//            return userService.getUserSummaryDetaialsForSalesManager(userId);
//        }
        }
        return ResponseEntity.ok(new ApiResponse(false, "get summary details unsuccessful", userId));
    }

    @GetMapping(ApiConstants.GET_USER_SUMMARY)
    public ResponseEntity<ApiResponse> getUserSummaryDetailsV(
            @RequestParam Integer userId,
            @RequestParam Integer roleId) {

        // Validate inputs
        if (userId == null || roleId == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "User ID and Role ID must not be null", null));
        }

        // Get role name from DB
        String roleName = roleService.getRoleNameById(roleId);

        if (roleName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Role not found for given Role ID", null));
        }

        Map<String, Integer> summaryDetails;

        if ("SALES".equalsIgnoreCase(roleName)) {
            // Individual sales data
            summaryDetails = userService.getUserSummaryDetaialsForSales(userId);

        } else if ("SALES_MANAGER".equalsIgnoreCase(roleName)) {
            // Get all salespersons under this manager
            List<Integer> salesPersonIds = userService.getUsersByRoleId(roleId)
                    .stream()
                    .map(User::getUserId)
                    .toList();

            summaryDetails = userService.getCumulativeSalesSummary(salesPersonIds);

        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Unsupported role for summary", null));
        }
        return ResponseEntity.ok(new ApiResponse(true, "Summary Details fetched successfully", summaryDetails));
    }



    @GetMapping(ApiConstants.GET_USER_NOTIFICATIONS)
    public ResponseEntity<ApiResponse> getUserNotifications(@RequestParam Integer userId) {
        if (userId == null) {
            return ResponseEntity.ok(new ApiResponse(false, "User ID must not be null", null));
        }

        List<NotificationDTO> notifications = userService.getUserNotifications(userId);

        if (notifications.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(false, "No Notifications", userId));
        }

        return ResponseEntity.ok(new ApiResponse(true, "Notifications fetched successfully", notifications));
    }

    @PostMapping(ApiConstants.UPDATE_BID_STATUS_FROM_CLIENT)
    public ResponseEntity<ApiResponse> updateBidStatusFromClient(@RequestParam(required = true) String status, @RequestParam(required = true)Integer quotationId) {

        if (status == null || status.isEmpty() || quotationId == null) {
            //log.error("status must not be null or Empty");
            return ResponseEntity.ok(new ApiResponse(false, "Status updation failure", status));
        } else
            userService.updateQuotationStatus(status, quotationId);
            return ResponseEntity.ok(new ApiResponse(true, "Status updation successful", status));
        }

    @PutMapping(ApiConstants.CHANGE_PASSWORD)
    public ResponseEntity<ApiResponse> changePassword(
            @RequestHeader("User-Id") Integer userId,
            @RequestBody Map<String, String> request) {
        try {
            ApiResponse response = userService.changePassword(userId, request);
            return ResponseEntity.status(response.isStatus() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to change password"));
        }
    }
}
