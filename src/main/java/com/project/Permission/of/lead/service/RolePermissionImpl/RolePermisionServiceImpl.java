package com.project.Permission.of.lead.service.RolePermissionImpl;

import com.project.Permission.of.lead.dto.RoleDto;
import com.project.Permission.of.lead.dto.RolePermissionDto;
import com.project.Permission.of.lead.entity.Permission;
import com.project.Permission.of.lead.entity.RolePermission;
import com.project.Permission.of.lead.entity.RolePermissionId;
import com.project.Permission.of.lead.entity.Roles;
import com.project.Permission.of.lead.mapper.RoleMapper;
import com.project.Permission.of.lead.mapper.RolePermissionMapper;
import com.project.Permission.of.lead.repository.PermissionRepository;
import com.project.Permission.of.lead.repository.RolePermissionRepository;
import com.project.Permission.of.lead.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;



@Service
@AllArgsConstructor

public class RolePermisionServiceImpl {


    private final RoleRepository roleRepo;


    private final PermissionRepository permissionRepo;

    @Autowired
    private final RolePermissionRepository rolePermissionRepo;


        @Transactional

        public RolePermissionDto assignPermissionsToRole(Long role_id, Set<Long> permissionId) {
// 1️⃣ Fetch the role
            Roles role = roleRepo.findById(role_id)
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            // 2️⃣ Fetch all permissions as entities
            Set<Permission> permissions = new HashSet<>();
            for (Long pid : permissionId) {
                Permission permission = permissionRepo.findById(pid)
                        .orElseThrow(() -> new RuntimeException("Permission not found: " + pid));
                permissions.add(permission);
            }

            // 3️⃣ Map to RolePermission entities
            Set<RolePermission> rolePermissions = RolePermissionMapper.mapToRolePermissions(
                    new RolePermissionDto(role_id, permissionId, true),
                    role,
                    permissions
            );

            // 4️⃣ Save all RolePermission mappings
            rolePermissionRepo.saveAll(rolePermissions);

            // 5️⃣ Return DTO indicating success
            return new RolePermissionDto(role_id, permissionId, true);

        }

}
