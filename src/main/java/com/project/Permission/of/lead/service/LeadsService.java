package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.LeadsDto;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LeadsService {
    LeadsDto createLead(LeadsDto leadsDto, Users loggedInUser, String eid, String buid);

    List<LeadsDto> getallLeads(String eid, String buid, Users loggedInUser, UserPrinciple userPrinciple);

    LeadsDto assignEmployee(LeadsDto leadsDto, String eid, String buid, String lid, Users loggedUser, UserPrinciple userPrinciple);

    LeadsDto assignTerritoryToLead(LeadsDto leadsDto, String eid, String buid, String lid,String tid, Users loggedInUser, UserPrinciple userPrinciple);

    LeadsDto assignEmployeeToLead(LeadsDto leadsDto, String eid, String buid, String lid, String tid, String empid, Users loggedInUser, UserPrinciple userPrinciple);

    Page<LeadsDto> findLeadByGlance(String eid, String buid, int pageNo);

    LeadsDto updateLeadStage(String eid, String buid, String lid, Users loggedInUser, LeadsDto leadsDto);

    LeadsDto createFollowUp(String eid, String buid, String lid, LeadsDto leadsDto, Users loggedInUser);

    LeadsDto getLeadsByLid(String eid, String buid, String lid, Users loggedInUser, UserPrinciple userPrinciple);

    LeadsDto updateLead(String eid, String buid, String lid, LeadsDto leadsDto, Users loggedInUser);

//    List<LeadsDto> getTerritorriesByBuidAndEid(String enterpriseId, String bussinessUnitId, UserPrinciple userPrinciple, Users loggedInUser);
}
