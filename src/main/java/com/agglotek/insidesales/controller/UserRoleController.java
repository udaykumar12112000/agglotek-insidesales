package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.dao.entity.Role;
import com.agglotek.insidesales.service.api.IRoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.agglotek.insidesales.constants.ApiConstants.*;

@RestController
@RequestMapping(ROLES_APIS)
public class UserRoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping(ADD_ROLE)
    public ResponseEntity<ApiResponse> addRole(@RequestBody Role roleData) {
        try {
            Role role = roleService.addRole(roleData);
            return ResponseEntity.ok(new ApiResponse(true, "Role Added Successfully!", role));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(new ApiResponse(false, "Failed to create Role!"));
    }

    @GetMapping(GET_ALL_ROLES)
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PutMapping(EDIT_ROLE)
    public ResponseEntity<ApiResponse> editRole(@RequestBody Role roleData) {
        try {
            Role role = roleService.editRole(roleData);
            return ResponseEntity.ok(new ApiResponse(true, "Role edited successfully!", role));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Role Not Found!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to edit role!"));
        }
    }

    @DeleteMapping(DELETE_ROLE)
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Integer roleId) {
        try {
            boolean deleted = roleService.deleteRoleById(roleId);
            if (deleted) {
                return ResponseEntity.ok(new ApiResponse(true, "Role deleted successfully!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, "Role not found or could not be deleted."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "An error occurred while deleting the role."));
        }
    }

}
