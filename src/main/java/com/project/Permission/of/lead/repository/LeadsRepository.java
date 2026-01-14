package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.dto.LeadsDto;
import com.project.Permission.of.lead.entity.Leads;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeadsRepository extends JpaRepository<Leads, Long> {
    List<Leads> findByEidAndBuid(String enterpriseId, String bussinessUnitId);

    Optional<Leads>findByLid(String leadId);

    String lid(String lid);



    Page<Leads> findByEidAndBuid(String eid, String eid1, Pageable pageable);



//    @Query(value = """
//        SELECT
//            l.lid,
//            l.lead_for,
//            l.created_at,
//
//            pg.product AS productGroupName,
//            pt.product AS productTypeName,
//            ps.product AS productSkuName,
//            sg.product AS serviceGroupName
//
//        FROM lms_lead l
//        LEFT JOIN product_catalogue pg ON l.product_group = pg.pid
//        LEFT JOIN product_catalogue pt ON l.product_type  = pt.pid
//        LEFT JOIN product_catalogue ps ON l.product_sku   = ps.pid
//        LEFT JOIN service_catalogue sg ON l.service_group = sg.pid
//
//        WHERE l.eid = :eid AND l.buid = :buid
//        ORDER BY l.created_at DESC
//        """, nativeQuery = true)
//    List<LeadsDto> getAllLeadsWithNames(@Param("eid") String eid,
//                                        @Param("buid") String buid);
}


