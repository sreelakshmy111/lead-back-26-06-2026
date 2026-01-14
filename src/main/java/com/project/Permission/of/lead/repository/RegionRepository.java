package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region,Long> {
//    List<Region> findByEnterpriseAndBussinessUnit(String enterpriseId, String bussinessUnitId);

    @Query("""
          SELECT r 
        FROM Region r 
        JOIN BussinessUnit b 
        ON r.bussinessUnitId = b.buid 
        WHERE b.enterpriseId = :enterpriseId 
        AND b.buid = :bussinessUnitId
    """)
    List<Region> findByEnterpriseAndBussinessUnit(
            @Param("enterpriseId") String  enterpriseId,
            @Param("bussinessUnitId") String bussinessUnitId
    );

    Optional<Region> findByRid(String regionId);
//    Region findByEnterpriseBussinessUnit(Enterprise enterprise, BussinessUnit bussinessUnit);


}
