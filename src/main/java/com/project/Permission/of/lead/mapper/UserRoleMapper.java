package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.UserRoleDto;
import com.project.Permission.of.lead.entity.*;

public class UserRoleMapper {

    // DTO → Entity
    public static UserRole mapToUserRole(Users user, Roles role) {
        UserRole userRole = new UserRole();

        userRole.setUid(user.getUid());
        userRole.setRoleId(role.getRoleId());

        return userRole;



    }

    // Entity → DTO
    public static UserRoleDto mapToUserRoleDto(UserRole userRole) {
        return new UserRoleDto(
                userRole.getUid(),       // ✅
                userRole.getRoleId()
        );
    }



}
