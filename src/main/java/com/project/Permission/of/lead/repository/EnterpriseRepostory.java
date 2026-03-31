package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnterpriseRepostory extends JpaRepository<Enterprise,Long> {

    Optional<Enterprise> findByEid(String enterpriseId);
//    boolean existsByCreatedBy(String loggedInUser);

    boolean existsByCreatedBy(String userId);

    List<Enterprise> findCreatedBy();

    List<Enterprise> findAllByCreatedBy(String userId);

    Enterprise findByCreatedBy(String userId);

    boolean existsByEid(String eid);

//    List<Enterprise> findAllById(List<String> enterpriseIds);

//    List<Enterprise> findAllByEid(List<String> enterpriseIds);

//    Optional<Object> findById(String enterpriseId);
}
