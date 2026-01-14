package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.LeadDto;
import com.project.Permission.of.lead.entity.Lead;
import com.project.Permission.of.lead.service.Impl.LeadServiceImpl;
import com.project.Permission.of.lead.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lead")
public class LeadController {

@Autowired
    private LeadService leadService;


    @PreAuthorize("hasAuthority('CREATE_CODEVALUE')")
    @PostMapping("/create")
    public ResponseEntity<LeadDto> createLead(@RequestBody LeadDto leadDto) {

        LeadDto lead=leadService.createLead(leadDto);
        return new ResponseEntity<>(lead, HttpStatus.CREATED);
    }


    // Only users with LEAD_VIEW permission can access this

    @PreAuthorize("hasAuthority('READ_CODEVALUE')")
    @GetMapping("/view")
    public List<LeadDto> getAllLeads() {
        return leadService.getAllLeads();
    }


    @PreAuthorize("hasAuthority('UPDATE_CODEVALUE')")
    @PutMapping("/update/{id}")
    public ResponseEntity<LeadDto> updateLead(@PathVariable("id") Long lead_id,@RequestBody LeadDto leadDto) {
        LeadDto updateLead=leadService.updateLead(lead_id,leadDto);
        return new ResponseEntity<>(updateLead, HttpStatus.OK);

    }

}
