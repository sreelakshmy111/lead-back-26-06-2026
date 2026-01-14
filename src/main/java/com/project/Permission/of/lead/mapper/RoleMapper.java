package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.RoleDto;
import com.project.Permission.of.lead.entity.Roles;

public class RoleMapper {

    public static Roles mapToRole(RoleDto roleDto) {
        return new Roles(
                roleDto.getRole_id(),
                roleDto.getRole_name(),
                roleDto.getDescription(),
                roleDto.isDisabled(),
                roleDto.getIsSystemRole(),
                roleDto.getIsSelfService(),
                roleDto.getPosition(),
                roleDto.getIdentifier(),
                roleDto.getLegalFormEnum()
        );
    }

    public static RoleDto mapToRoleDto(Roles role) {
        return new RoleDto(
                role.getRole_id(),
                role.getRole_name(),
                role.getDescription(),
                role.isDisabled(),      //maintain same order in entity
                role.getIsSystemRole(),
                role.getIsSelfService(),
                role.getPosition(),
                role.getIdentifier(),
                role.getLegalFormEnum()
        );
    }


}
