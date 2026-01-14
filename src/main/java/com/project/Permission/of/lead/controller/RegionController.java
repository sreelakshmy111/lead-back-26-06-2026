package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.RegionDto;
import com.project.Permission.of.lead.entity.Region;
import com.project.Permission.of.lead.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/enterprises")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/region")
    public ResponseEntity<?> getRegions(
            @RequestParam String eid,
            @RequestParam String buid) {

        if (buid.endsWith("/regions")) {

            String enterpriseId = eid.split("/")[0];
            String businessUnitId = buid.split("/")[0];

            return ResponseEntity.ok(
                    businessUnitId
            );
        }

        return ResponseEntity.ok("get enterprise id and bussinessunit id");
    }

    @GetMapping("/regions")
    public ResponseEntity<List<RegionDto>> getAllRegions() {

        List<RegionDto> regions = regionService.findAllRegion();
        return ResponseEntity.status(HttpStatus.OK).body(regions);

    }



}
