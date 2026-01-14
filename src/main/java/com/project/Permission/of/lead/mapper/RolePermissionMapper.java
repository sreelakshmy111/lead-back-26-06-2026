package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.RolePermissionDto;
import com.project.Permission.of.lead.entity.Permission;
import com.project.Permission.of.lead.entity.RolePermission;
import com.project.Permission.of.lead.entity.RolePermissionId;
import com.project.Permission.of.lead.entity.Roles;


import java.util.HashSet;
import java.util.Set;

public class RolePermissionMapper {

    public static Set<RolePermission> mapToRolePermissions(RolePermissionDto dto, Roles role, Set<Permission> permissions) {
        Set<RolePermission> rolePermissions = new HashSet<>();

        for (Permission permission : permissions) {
            RolePermissionId id = new RolePermissionId(role.getRole_id(), permission.getPermission_id());
            RolePermission rp = new RolePermission();
            rp.setId(id);
            rp.setRole(role);
            rp.setPermission(permission);
            rp.setHasFullAccess(dto.isHasFullAccess());

            rolePermissions.add(rp);
        }

        return rolePermissions;
    }

 public static RolePermissionDto mapToRolePermissionDto(RolePermission rolePermission) {

     Set<Long> permissionIds = new HashSet<>();
     permissionIds.add(rolePermission.getPermission().getPermission_id());

     return new RolePermissionDto(
             rolePermission.getRole().getRole_id(),
             permissionIds,
             rolePermission.isHasFullAccess()
     );
 }



}
