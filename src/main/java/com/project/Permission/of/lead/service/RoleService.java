package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.RoleDto;
import com.project.Permission.of.lead.entity.Roles;
import com.project.Permission.of.lead.mapper.RoleMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface RoleService {

    RoleDto createRole(RoleDto roleDto);

    List<RoleDto> getAllRoles();

//    RoleDto assignPermissionsToRole(Long role_id, Set<Long> permission_id);








}
