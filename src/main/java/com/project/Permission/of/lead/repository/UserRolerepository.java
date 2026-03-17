package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Roles;
import com.project.Permission.of.lead.entity.UserRole;
import com.project.Permission.of.lead.entity.UserRoleId;
import com.project.Permission.of.lead.entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRolerepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUser(Users user);


    @Modifying
    @Transactional
    @Query("DELETE FROM UserRole ur WHERE ur.user = :user AND ur.role.id IN :roleIds")
    void deleteRoles(@Param("user") Users user, @Param("roleIds") List<Long> roleIds);
}
