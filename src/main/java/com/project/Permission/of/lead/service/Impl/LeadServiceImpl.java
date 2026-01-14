package com.project.Permission.of.lead.service.Impl;

import com.project.Permission.of.lead.dto.LeadDto;
import com.project.Permission.of.lead.entity.Lead;
import com.project.Permission.of.lead.mapper.LeadMapper;
import com.project.Permission.of.lead.repository.LeadRepository;
import com.project.Permission.of.lead.service.LeadService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor

public class LeadServiceImpl implements LeadService {

    private LeadRepository leadrepo;


    @PreAuthorize("hasAuthority('CREATE_CODEVALUE')")
    public LeadDto createLead(LeadDto leadDto) {

        Lead lead = LeadMapper.mapToLead(leadDto);

        if (leadrepo.existsByEmail(lead.getEmail())) {
            throw new RuntimeException("Lead already exists");
        }
        Lead saveLead = leadrepo.save(lead);
        return LeadMapper.mapToLeadDto(saveLead);

    }

    @Override
    @PreAuthorize("hasAuthority('READ_CODEVALUE')")  // Only users with this permission
    public List<LeadDto> getAllLeads() {
        return leadrepo.findAll()
                .stream()
                .map(LeadMapper::mapToLeadDto)
                .collect(Collectors.toList());
    }



    @Override
    @PreAuthorize("hasAuthority('UPDATE_CODEVALUE')")
    public LeadDto updateLead(Long lead_id,LeadDto leadDto) {
        Lead lead = leadrepo.findById(lead_id)
                .orElseThrow(() -> new RuntimeException("Lead not found with id: " + lead_id));

        lead.setLeadName(leadDto.getLeadName());
        lead.setEmail(leadDto.getEmail());
        lead.setPhone(leadDto.getPhone());

        Lead saveLead = leadrepo.save(lead);
        return LeadMapper.mapToLeadDto(saveLead);
    }

}