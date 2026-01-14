package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.RegionDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface RegionService {
    RegionDto createRegions(RegionDto regionDto, String eid, String buid, Users loggedInUser);



    List<RegionDto> getRegions(String eid, String buid);

    RegionDto updateRegion(RegionDto regionDto, Users loggedUser, String eid, String buid, String rid);

    List<RegionDto> findAllRegion();

    RegionDto getRegionById(String eid, String buid, String rid);

//    RegionDto getRegions(Long enterpriseId, Long bussinessUnitId);

  
//    RegionDto createRegion(RegionDto regionDto);
//    void deleteRegion(Long id);
//
//    List<RegionDto> getAllRegions();
//
//    RegionDto getRegionById(Long id);
}
