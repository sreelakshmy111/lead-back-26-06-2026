package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.LeadDto;

import java.util.List;

public interface LeadService {
    LeadDto createLead (LeadDto leadDto);

    List<LeadDto> getAllLeads();

    LeadDto updateLead(Long lead_id,LeadDto leadDto);
}
