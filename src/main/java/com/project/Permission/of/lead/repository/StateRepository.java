package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface StateRepository extends JpaRepository<State,Long> {

    @Query("""
        SELECT s from State s
        WHERE s.zoneId=:zoneId
        AND s.countryId=:countryId
        AND s.regionId=:regionId
        
        AND s.bussinessUnitId=:bussinessUnitId
        
        """)

    List<State> findByZoneIdAndCountryIdAndRegionIdAndBussinessUniId(
           @Param("zoneId") String zoneId,
           @Param("countryId") String countryId,
           @Param("regionId") String regionId,
           @Param("bussinessUnitId") String bussinessUnitId);

    Optional<State> findBySid(String stateId);
}
