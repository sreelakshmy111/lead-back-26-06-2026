package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.PermissionDto;
import com.project.Permission.of.lead.entity.Permission;
import com.project.Permission.of.lead.entity.Roles;

import java.util.Set;

public class PermissionMapper {


    public static PermissionDto mapToPermissionDto(Permission permission) {
        return new PermissionDto(
                permission.getPermission_id(),
                permission.getActionName(),
                permission.getCode(),
                permission.getEntityName(),
                permission.getGrouping(),
                permission.isCanMakerChecker(),
                permission.getDescription()

        );
    }

public static Permission mapToPermission(PermissionDto dto) {
        return new Permission(
                dto.getPermissionId(),
                dto.getActionName(),
                dto.getCode(),
                dto.getEntityName(),
                dto.getGrouping(),
                dto.isCanMakerChecker(),
                dto.getDescription()

        );
}





}
