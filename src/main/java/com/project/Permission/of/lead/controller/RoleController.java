package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.RoleDto;
import com.project.Permission.of.lead.dto.RolePermissionDto;
import com.project.Permission.of.lead.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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



}
