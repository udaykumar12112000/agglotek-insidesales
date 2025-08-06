package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.constants.AppConstants;
import com.agglotek.insidesales.dao.entity.User;
import com.agglotek.insidesales.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

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

        if(!user.getEditorRoleName().equals(AppConstants.ADMIN))
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
        if(users.isEmpty()){
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


}
