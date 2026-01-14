package com.project.Permission.of.lead.controller;


import com.project.Permission.of.lead.dto.RoleDto;
import com.project.Permission.of.lead.dto.RolePermissionDto;
import com.project.Permission.of.lead.dto.UserRoleDto;
import com.project.Permission.of.lead.service.RolePermissionImpl.RolePermisionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("role/assign")
public class RolePermissionController {

    @Autowired
    private RolePermisionServiceImpl rolepermisionService;


    // Assign permissions to a role
    @PostMapping("/permissions")
    public ResponseEntity<RolePermissionDto> assignPermissionsToRole(@RequestBody RolePermissionDto dto) {

            RolePermissionDto updatedRole = rolepermisionService.assignPermissionsToRole(dto.getRoleId(), dto.getPermissionIds());
        return ResponseEntity.ok(updatedRole);
    }


}
