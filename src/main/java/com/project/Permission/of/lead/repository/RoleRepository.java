package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {


//    Role findByroleName(String enterpriseAdmin);


    Optional<Roles> findByRoleName(String roleName);


//    Roles findByRoleId(Long roleId);

//    Roles findByRole_id(Long roleId);

    Optional<Roles> findById(Long id);
}
