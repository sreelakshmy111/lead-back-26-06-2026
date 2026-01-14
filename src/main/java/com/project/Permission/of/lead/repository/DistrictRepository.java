package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("""
            SELECT d FROM District d
            WHERE d.stateId=:stateId
            AND d.zoneId=:zoneId
            AND d.countryId=:countryId
            AND d.regionId=:regionId
            AND d.bussinessUnitId=:bussinessUnitId
            
            """)


    List<District> findByStateIdAndZoneIdAndCountryIdAndRegionIdAndBussinessUnitId(
           @Param("stateId") String stateId,
            @Param("zoneId") String zoneId,
           @Param("countryId") String countryId,
           @Param("regionId") String regionId,
           @Param("bussinessUnitId") String bussinessUnitId);

    Optional<District> findByDid(String districtId);
}
