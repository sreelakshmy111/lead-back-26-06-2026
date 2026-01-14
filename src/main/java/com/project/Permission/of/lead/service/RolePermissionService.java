package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.RolePermissionDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface RolePermissionService {

    RolePermissionDto assignPermissionsToRole(@RequestBody RolePermissionDto dto);

}
