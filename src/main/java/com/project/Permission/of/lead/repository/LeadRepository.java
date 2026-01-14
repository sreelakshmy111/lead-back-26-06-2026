package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends JpaRepository<Lead,Long> {
    boolean existsByEmail(String email);
}
