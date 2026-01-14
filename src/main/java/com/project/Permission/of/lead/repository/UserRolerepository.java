package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.UserRole;
import com.project.Permission.of.lead.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolerepository extends JpaRepository<UserRole, UserRoleId> {
}
