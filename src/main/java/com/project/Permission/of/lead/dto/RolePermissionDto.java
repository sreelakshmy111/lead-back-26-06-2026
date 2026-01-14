package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDto {

    private Long roleId;
    private Set<Long> permissionIds;
    private boolean hasFullAccess = true; // optional
    
}
