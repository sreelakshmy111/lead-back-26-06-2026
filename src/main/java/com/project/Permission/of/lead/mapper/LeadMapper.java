package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.LeadDto;
import com.project.Permission.of.lead.entity.Lead;

public class LeadMapper {

    public static Lead mapToLead(LeadDto leadDto){

        return new Lead(
                leadDto.getLead_id(),
                leadDto.getLeadName(),
                leadDto.getEmail(),
                leadDto.getPhone()
        );


    }

    public static LeadDto mapToLeadDto(Lead lead){

    return new LeadDto(
            lead.getLead_id(),
            lead.getLeadName(),
            lead.getEmail(),
            lead.getPhone()
        );
    }
}


