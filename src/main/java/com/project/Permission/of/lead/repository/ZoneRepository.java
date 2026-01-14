package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone,Long> {
@Query("""
        SELECT z from Zone z
        WHERE z.countryId=:countryId
        AND z.regionId=:regionId
        AND z.bussinessUnitId=:bussinessUnitId
        """)
List<Zone> findByCountryIdAndRegionIdAndBussinessUnitId(
          @Param("countryId") String countryId,
          @Param("regionId") String regionId,
          @Param("bussinessUnitId") String bussinessunitId);


    Optional<Zone> findByZid(String zoneId);
}
