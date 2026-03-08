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

    @GetMapping("/name/{roleId}")
    public ResponseEntity<ApiResponse> getRoleName(@PathVariable Integer roleId) {
        String roleName = roleService.getRoleNameById(roleId);
        if (roleName != null) {
            return ResponseEntity.ok(new ApiResponse(true, "Role name fetched successfully!", roleName));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Role not found"));
        }
    }

}
