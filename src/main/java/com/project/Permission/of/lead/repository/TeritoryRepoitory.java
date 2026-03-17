package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Teritory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TeritoryRepoitory extends CrudRepository<Teritory,Long> {

    @Query("""
            
            SELECT t FROM Teritory t
            WHERE t.districtId=:districtId
            AND t.stateId=:stateId
            AND t.zoneId=:zoneId
            AND t.countryId=:countryId
            AND t.regionId=:regionId
            AND t.bussinessUnitId=:bussinessUnitId 
            
            
            """)
    List<Teritory> findByDistrictIdAndStateIdAndZoneIdAndCountryIdAndRegionIdAndBussinessUnitId(
          @Param("districtId") String districtId,
           @Param("stateId") String stateId,
          @Param("zoneId") String zoneId,
          @Param("countryId") String countryId,
          @Param("regionId") String regionId,
          @Param("bussinessUnitId") String bussinessUnitId);

    Optional<Teritory> findByTid(String teritoryId);

    List<Teritory> findByTidIn(List<String> territoriesIds);

//    List<Teritory> findByBuid(String bussinessUnitId);

    List<Teritory> findByBussinessUnitId(String bussinessUnitId);

    boolean existsByBussinessUnitId(String buid);

//    Optional<Teritory> findByTid(String territoryId);
}
