package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Catalogue.ProductCatalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCatalogueRepository extends JpaRepository<ProductCatalogue, Long> {
    Optional<ProductCatalogue> findByPid(String productGroupIdId);

//    List<ProductCatalogue> findByEidandBuidandPid(String enterpriseId, String bussinessUnitId, String productGroupId);

//    @Query("SELECT p FROM ProductCatalogue p WHERE p.eid = :eid AND p.buid = :buid AND p.pid = :pid")
//    List<ProductCatalogue> getProducts(@Param("eid") String eid,
//                                       @Param("buid") String buid,
//                                       @Param("pid") String pid);

    List<ProductCatalogue> findByEidAndBuidAndPid(String enterpriseId, String bussinessUnitId, String productGroupId);

    List<ProductCatalogue> findByEidAndBuid(String enterpriseId, String bussinessUnitId);

//    List<ProductCatalogue> findByEidAndBuidAndParentId(String enterpriseId, String bussinessUnitId, String productGroupId);


//    List<ProductCatalogue> findTypesByProductGroup(String eid, String buid, String parentId);

    @Query("SELECT p FROM ProductCatalogue p WHERE p.eid = :eid AND p.buid = :buid AND p.pid LIKE 'PG%'")
    List<ProductCatalogue> findDistinctProductGroups(@Param("eid") String eid,
                                                     @Param("buid") String buid);


@Query("SELECT p FROM ProductCatalogue p WHERE p.eid = :eid AND p.buid = :buid AND p.parentId = :parentId")
List<ProductCatalogue> findByEidAndBuidAndParentId(
        @Param("eid") String eid,
        @Param("buid") String buid,
        @Param("parentId") String parentId);

    List<ProductCatalogue> findByParentId(String productGroupId);

//    List<ProductCatalogue> findProductGroups(String enterpriseId, String bussinessUnitId);
}
