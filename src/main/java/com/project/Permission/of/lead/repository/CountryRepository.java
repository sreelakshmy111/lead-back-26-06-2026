package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {


    @Query("""
    SELECT c 
    FROM Country c
    WHERE c.bussinessUnitId = :bussinessUnitId
    AND c.regionId = :regionId
""")

    List<Country> findByBussinessUnitIdAndRegionId(

//             @Param("enterpriseId") Long enterpriseId,
             @Param("bussinessUnitId") String busssinessUnitId,
             @Param("regionId") String regionId

            );

    Optional<Country> findByCid(String countryId);
}
