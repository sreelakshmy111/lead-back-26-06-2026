package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.RolePermission;
import com.project.Permission.of.lead.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {

//    <T> ScopedValue<T> findById(Long roleId);
}
