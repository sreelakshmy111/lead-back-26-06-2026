package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Enterprise;
import com.project.Permission.of.lead.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnterpriseRepostory extends JpaRepository<Enterprise,Long> {

    Optional<Enterprise> findByEid(String enterpriseId);
    boolean existsByCreatedBy(Users loggedInUser);

    boolean existsByCreatedBy(Long userId);

    List<Enterprise> findCreatedBy();

    List<Enterprise> findAllByCreatedBy(Long userId);

    Enterprise findByCreatedBy(Long userId);

//    List<Enterprise> findAllById(List<String> enterpriseIds);

//    List<Enterprise> findAllByEid(List<String> enterpriseIds);

//    Optional<Object> findById(String enterpriseId);
}
