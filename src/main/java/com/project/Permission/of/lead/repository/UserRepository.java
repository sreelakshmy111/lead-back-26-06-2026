package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Roles;
import com.project.Permission.of.lead.entity.UserRole;
import com.project.Permission.of.lead.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByUsername(String username);

    boolean existsByUsername(String username);

    Users findByEmail(String email);

    boolean existsByEmail(String email);


    Optional<Users> findByUid(String uid);

//    Optional<Users> findByEmail(String userId);

//    Users findByEmail(String email);

//    boolean existsByEmail(String email);

//    Optional<Users> findByEmpId(String employeeId);
}


