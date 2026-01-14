package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Catalogue.ServiceCatalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceCatalogueRepository  extends JpaRepository<ServiceCatalogue,Long> {
    Optional<ServiceCatalogue> findBySid(String serviceGroupId);

    List<ServiceCatalogue> findByEidAndBuid(String enterpriseId, String bussinessUnitId);

    List<ServiceCatalogue> findByParentId(String serviceGroupId);

    @Query("SELECT s FROM ServiceCatalogue s WHERE s.eid = :eid AND s.buid = :buid AND s.parentId = :parentId")
    List<ServiceCatalogue> findByEidAndBuidAndParentId(
            @Param("eid") String eid,
            @Param("buid") String buid,
            @Param("parentId") String parentId);

    @Query("SELECT s from ServiceCatalogue s WHERE s.eid = :eid AND s.buid = :buid AND s.sid LIKE 'SG%'")
    List<ServiceCatalogue> findDistinctServiceGroup(
            @Param("eid") String eid,
            @Param("buid") String buid);

//    List<ServiceCatalogue> findByEnterpriseIdAndBussinessUnitId(String enterpriseId, String bussinessUnitId);
}
