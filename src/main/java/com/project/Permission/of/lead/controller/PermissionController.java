package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.PermissionDto;
import com.project.Permission.of.lead.service.PermissionService;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService  service;

    @PostMapping("/set")
    public ResponseEntity<PermissionDto> setPermission(@RequestBody PermissionDto permissionDto) {

        PermissionDto permission=service.setPermission(permissionDto);
        return ResponseEntity.ok(permission);

    }

}
