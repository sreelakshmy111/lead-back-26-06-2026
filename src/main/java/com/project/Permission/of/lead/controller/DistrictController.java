package com.project.Permission.of.lead.controller;


import com.project.Permission.of.lead.dto.DistrictDto;
import com.project.Permission.of.lead.dto.StateDto;
import com.project.Permission.of.lead.service.DistrictService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/districts")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

//
//    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
//    @PostMapping("")
//    public ResponseEntity<DistrictDto> createDistrict(@RequestBody DistrictDto districtDto) {
//        DistrictDto saved = districtService.createDistrict(districtDto);
//        return ResponseEntity.ok(saved);
//    }
//
//
    @GetMapping("/districts")
    public ResponseEntity<List<DistrictDto>> getAllDistricts() {

        List<DistrictDto> districtDtos=districtService.getAllDistricts();
        return ResponseEntity.ok(districtDtos);
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<DistrictDto> getDistrictById(@PathVariable long id) {
//        DistrictDto dto=districtService.getDistrictById(id);
//        return ResponseEntity.ok(dto);
//
//
//    }
}