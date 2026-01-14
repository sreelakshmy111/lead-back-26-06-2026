package com.project.Permission.of.lead.controller;


import com.project.Permission.of.lead.dto.CountryDto;
import com.project.Permission.of.lead.dto.ZoneDto;
import com.project.Permission.of.lead.entity.Zone;
import com.project.Permission.of.lead.repository.ZoneRepository;
import com.project.Permission.of.lead.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ZoneController {

    @Autowired
    private ZoneService zoneService;
//
//    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
//    @PostMapping("")
//    public ResponseEntity<ZoneDto> createZone(@RequestBody ZoneDto zoneDto){
//
//        ZoneDto zoneDto1=zoneService.createZone(zoneDto);
//        return ResponseEntity.ok(zoneDto1);
//
//    }

    @GetMapping("/zones")
    public ResponseEntity<List<ZoneDto>> getAllZones(){

        List<ZoneDto> zoneDtos=zoneService.getAllZones();
        return ResponseEntity.ok(zoneDtos);
    }

//
//    @GetMapping("/{id}")
//    public ResponseEntity<ZoneDto> getZoneById(@PathVariable long id){
//
//        ZoneDto dto=zoneService.getZoneById(id);
//        return ResponseEntity.ok(dto);
//
//    }
//
}
