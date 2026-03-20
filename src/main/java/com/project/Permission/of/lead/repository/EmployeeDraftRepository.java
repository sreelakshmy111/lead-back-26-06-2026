package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.EmployeeDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeDraftRepository extends JpaRepository<EmployeeDraft,Long> {

//    static boolean existsByEmail(String email) {
//    }

    boolean existsByEmail(String email);

    EmployeeDraft findByCreatedBy(String userId);

//    Optional<Object> findByEmpdId(Long empDraftId);
}
