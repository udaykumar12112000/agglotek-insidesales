package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.constants.AppConstants;
import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.service.api.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(ApiConstants.USER_APIS)
public class UserController {
    @Autowired
    private IUserService userService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping(ApiConstants.CREATE_USER)
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {

        if (user.getName() == null || user.getName().isEmpty())
            return ResponseEntity.status(500).body(new ApiResponse(false, "User name must not be empty"));

        if (!user.getEditorRoleName().equals(AppConstants.ADMIN))
            return ResponseEntity.ok(new ApiResponse(false, "User don't have permission"));
        user.setPassword(AppConstants.DEFAULT_PASSWORD);
        userService.addUser(user);
        return ResponseEntity.ok(new ApiResponse(true, "Created user successfully"));

    }

    @GetMapping(ApiConstants.GET_USER)
    public List<User> getUsers(@RequestParam(required = false) Integer userId,
                               @RequestParam(required = false) Integer roleId) {
        if (userId != null) {
            return userService.getUserByUserId(userId);
        } else if (roleId != null) {
            return userService.getUsersByRoleId(roleId);
        } else {
            return userService.getAllUsers();
        }
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
        return ResponseEntity.ok(new ApiResponse(true, "Edit user successful"));
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

    @GetMapping(ApiConstants.GET_USER_SUMMARY)
    public Map<String, Integer> getUserSummaryDetaials(@RequestParam(required = true) Integer userId,
                                                      @RequestParam(required = true) Integer roleId) {

        if (userId == null || roleId == null) {
            log.error("User Id and role Id must not be null");
        } else if (roleId == 2) {
            return userService.getUserSummaryDetaialsForSales(userId);
//        } else if (roleId == 3) {
//            return userService.getUserSummaryDetaialsForSalesManager(userId);
//        }
        }
        return new HashMap<>();
    }

    @GetMapping(ApiConstants.GET_USER_NOTIFICATIONS)
    public List<String> getUserNotifications(@RequestParam(required = true) Integer userId) {

        if (userId == null) {
            log.error("User Id must not be null");
            return new ArrayList<>();
        } else {
            return userService.getUserNotifications(userId);
        }
    }

    @PostMapping(ApiConstants.UPDATE_BID_STATUS_FROM_CLIENT)
    public void updateBidStatudFromClient(@RequestParam(required = true) String status, @RequestParam(required = true)Integer quotationId) {

        if (status == null || status.isEmpty() || quotationId == null) {
            log.error("status must not be null or Empty");
            return;
        } else {
            userService.updateQuotationStatus(status, quotationId);
        }
    }
}
