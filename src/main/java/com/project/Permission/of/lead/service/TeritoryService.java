package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.TeritoryDto;
import com.project.Permission.of.lead.entity.Teritory;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;

import java.util.List;

public interface TeritoryService {
    TeritoryDto createTeritory(TeritoryDto teritoryDto, Users loggedInUser, String did, String sid, String zid, String cid, String rid, String buid, String eid);

    List<TeritoryDto> getTeritories(String did, String sid, String zid, String cid, String rid, String buid, String eid);

    TeritoryDto updateTeritory(TeritoryDto teritoryDto, Users loggedUser, String tid, String did, String sid, String zid, String cid, String rid, String buid, String eid);

//    TeritoryDto getTeritorieById(String tid, String did, String sid, String zid, String cid, String rid, String buid, String eid);

    List<TeritoryDto> getTerritorriesByBuidAndEid(String eid, String buid, UserPrinciple userPrinciple, Users loggedInUser);

    TeritoryDto getTeritorieById(String did, String sid, String zid, String cid, String rid, String buid, String eid, String tid);
//    TeritoryDto createTeritory(TeritoryDto teritoryDto);

//    List<TeritoryDto> getAll();

//    TeritoryDto getTeritoryById(Long id);
}
