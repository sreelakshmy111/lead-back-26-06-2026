package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.ZoneDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface ZoneService {
    ZoneDto createZone(ZoneDto zoneDto, String eid, String buid, String rid, String cid, Users loggedInUser);

    List<ZoneDto> getZones(String cid, String rid, String buid, String eid);

    ZoneDto updateZones(ZoneDto zoneDto, Users loggedUser, String zid, String cid, String rid, String buid, String eid);

    List<ZoneDto> getAllZones();

    ZoneDto getZonesById(String cid, String rid, String buid, String eid, String zid);
//    ZoneDto createZone(ZoneDto zoneDto);

//    List<ZoneDto> getZones();

//    ZoneDto getZoneById(long id);
}
