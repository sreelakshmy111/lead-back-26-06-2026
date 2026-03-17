package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.RoleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    RoleDto createRole(RoleDto roleDto);

    List<RoleDto> getAllRoles();

    List<RoleDto> getExistRolesOfEmployee(String eid, String buid, String empId);

    void deleteUserRoles(String eid, String buid, String employeeId, List<Long> roleIds);

//    RoleDto assignPermissionsToRole(Long role_id, Set<Long> permission_id);








}
