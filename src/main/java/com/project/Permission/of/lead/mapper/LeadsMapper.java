package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.LeadsDto;
import com.project.Permission.of.lead.entity.Leads;

import java.time.LocalDateTime;

public class LeadsMapper {

    public static Leads mapToLeads(LeadsDto leadsDto, Long userId, String enterpriseId, String busssinessunitId) {
        LocalDateTime now=LocalDateTime.now();
        return new Leads(
                leadsDto.getId(),
                leadsDto.getLid(),
                leadsDto.getLeadSource(),
                leadsDto.getLeadFor(),
                leadsDto.getProductGroup(),
                leadsDto.getProductType(),
                leadsDto.getProductSku(),
                leadsDto.getServiceGroup(),
                leadsDto.getServiceType(),
                leadsDto.getServiceItem(),
                leadsDto.getEmployeeId(),
                leadsDto.getTerritoryId(),
                leadsDto.getContactId(),
                enterpriseId,
                busssinessunitId,
                leadsDto.isActive(),
                leadsDto.getCreated_at()!=null? leadsDto.getCreated_at():now,
                userId,
                null,
                leadsDto.getUpdated_by(),
                leadsDto.getLeadStatus(),
                leadsDto.getNextFollowUp(),
                leadsDto.getNote()
        );
    }

    public static LeadsDto mapToLeadsDto(Leads leads) {
        return new LeadsDto(
                leads.getId(),
                leads.getLid(),
                leads.getLeadSource(),
                leads.getLeadFor(),
                leads.getProductGroup(),
                leads.getProductType(),
                leads.getProductSku(),
                leads.getServiceGroup(),
                leads.getServiceType(),
                leads.getServiceItem(),
                leads.getEmployeeId(),
                leads.getTerritoryId(),
                leads.getContactId(),
                leads.getEid(),
                leads.getBuid(),
                leads.isActive(),
                leads.getCreatedAt(),
                leads.getCreated_by(),
                leads.getUpdated_at(),
                leads.getUpdated_by(),
                leads.getLeadStatus(),
                leads.getNextFollowUp(),
                leads.getNote()

        );
    }


}
