package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.RoleDto;
import com.project.Permission.of.lead.service.RoleService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class RoleController {

    @Autowired
    private RoleService service;

    @PostMapping("role/create")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto){
        RoleDto createRole=service.createRole(roleDto);
        return ResponseEntity.ok(createRole);

    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getAllRoles(){
        List<RoleDto> roles=service.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    /// Fetch exist employeee roles.......................................................

    @GetMapping("enterprises/{eid}/bussinessunits/{buid}/employee/{empId}/roles")
    public ResponseEntity<List<RoleDto>> getAssignedRole(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                         @PathVariable String eid,
                                                         @PathVariable String buid,
                                                         @PathVariable String empId){



     List<RoleDto> r=service.getExistRolesOfEmployee(eid,buid,empId);
     return ResponseEntity.ok(r);


    }

    ///  DELETE ROLES.............................................
    @DeleteMapping("enterprises/{eid}/bussinessunits/{buid}/employee/{employeeId}/roles/{roleIds}")
    public ResponseEntity<?> deleteEmployeeRoles(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                      @PathVariable String eid,
                                                      @PathVariable String buid,
                                                      @PathVariable String employeeId,
                                                      @PathVariable List<Long> roleIds){

        service.deleteUserRoles(eid,buid,employeeId,roleIds);
        return ResponseEntity.ok("roles deleted");

    }


}
