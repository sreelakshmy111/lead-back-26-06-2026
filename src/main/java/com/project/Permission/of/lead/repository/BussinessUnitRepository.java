package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.BussinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BussinessUnitRepository extends JpaRepository<BussinessUnit, Long> {
    List<BussinessUnit> findByEnterpriseId(String enterpriseId);


    Optional<BussinessUnit> findByBuid(String bussinessUnitId);

    Optional<BussinessUnit> findByEnterpriseIdAndBuid(String enterpriseId, String buid);


//    List<Enterprise> findByCreateById(Long userId);

    List<BussinessUnit> findByCreatedBy(String userId);

    boolean existsByEnterpriseId(String id);

    boolean existsByBuidBAndEnterpriseId(String buid, String eid);
}
