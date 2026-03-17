package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.UserRoleDto;
import com.project.Permission.of.lead.entity.*;

public class UserRoleMapper {

    // DTO → Entity
    public static UserRole mapToUserRole(Users user, Roles role) {
        // 1️⃣ Create composite key
        UserRoleId id = new UserRoleId(user.getUser_id(), role.getRoleId());

        // 2️⃣ Create new entity
        UserRole userRole = new UserRole();
        userRole.setId(id);
        userRole.setUser(user);
        userRole.setRole(role);

        return userRole;

    }

    // Entity → DTO
    public static UserRoleDto mapToUserRoleDto(UserRole userRole) {
        return new UserRoleDto(
                userRole.getUser().getUser_id(),
                userRole.getRole().getRoleId()
        );
    }



}
