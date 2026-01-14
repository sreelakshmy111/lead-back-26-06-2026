package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.DistrictDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface DistrictService {
    DistrictDto createDistrict(DistrictDto districtDto, Users loggedInUser, String sid, String zid, String cid, String rid,  String buid, String ed);

    List<DistrictDto> getDistricts(String sid, String zid, String ciId, String rid, String buid, String eid);

    DistrictDto updateDistrict(DistrictDto districtDto, Users loggedUser, String did, String sid, String zid, String cid, String rid, String buid, String eid);

    List<DistrictDto> getAllDistricts();

    DistrictDto getDistrictById(String did, String sid, String zid, String cid, String rid, String buid, String eid);
//    DistrictDto createDistrict(DistrictDto districtDto);

//    List<DistrictDto> getAllDistricts();

//    DistrictDto getDistrictById(long id);
}
