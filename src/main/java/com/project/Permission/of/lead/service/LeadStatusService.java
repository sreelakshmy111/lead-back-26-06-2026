package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.LeadStatusCustomDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface LeadStatusService {
    LeadStatusCustomDto leadStatus(String eid, String buid, LeadStatusCustomDto leadStatusCustomDto, Users loggedInUser);

    List<LeadStatusCustomDto> getLeadStages(String eid, String buid, Users loggedInUser);

    LeadStatusCustomDto createNewStage(String eid, String buid, LeadStatusCustomDto leadStatusCustomDto, Users loggedInUser);

    LeadStatusCustomDto deleteStage(String eid, String buid, Long stageId);

    LeadStatusCustomDto editLeadsStage(String eid, String buid, Long stageId, LeadStatusCustomDto leadStatusCustomDto);

    void moveStage(String eid, String buid, Long stageId, String direction);

    LeadStatusCustomDto findLeadByAsc(String eid, String buid, Users loggedInUser);

//    LeadStatusCustomDto saveLeadStage(String eid, String buid, String lid, Users loggedInUser);
}
