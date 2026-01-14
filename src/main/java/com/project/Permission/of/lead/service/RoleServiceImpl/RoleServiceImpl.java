package com.project.Permission.of.lead.service.RoleServiceImpl;

import com.project.Permission.of.lead.dto.RoleDto;
import com.project.Permission.of.lead.entity.Permission;
import com.project.Permission.of.lead.entity.Roles;
import com.project.Permission.of.lead.mapper.RoleMapper;
import com.project.Permission.of.lead.repository.PermissionRepository;
import com.project.Permission.of.lead.repository.RoleRepository;
import com.project.Permission.of.lead.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {



    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;  // ✅ add this

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Roles createRole = RoleMapper.mapToRole(roleDto);
        Roles savedRole = roleRepo.save(createRole);
        return RoleMapper.mapToRoleDto(savedRole);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Roles> roles=roleRepo.findAll();
        return roles.stream().map(r->RoleMapper.mapToRoleDto(r)).collect(Collectors.toList());
    }


}
