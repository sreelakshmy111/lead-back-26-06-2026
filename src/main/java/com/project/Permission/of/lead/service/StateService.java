package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.StateDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface StateService {
    StateDto createState(StateDto stateDto, Users loggedInUser, String zoneId, String countryId, String regionId, String bussinessUnitId, String enterpriseId);

    List<StateDto> getStates(String zid, String cid, String rid, String buid, String eid);

    StateDto updateState(StateDto stateDto, Users loggedUser, String sid, String zid, String cid, String rid, String buid, String eid);

    List<StateDto> getAllStates();

    StateDto getStateById(String zid, String cid, String rid, String buid, String eid,String sid);
//    StateDto createState(StateDto stateDto);

//    List<StateDto> findAll();

//    StateDto getStateById(long id);
}
