package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/enterprises")


public class SampleController {


    private boolean hasRole(UserPrinciple userPrinciple, String role) {
        return userPrinciple.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }


    // 1. Remove "/hello1" from GetMapping because it's part of the parameter value

    // 🔹 Get Business Units for Enterprise
    @GetMapping(params = {"eid"})
    public ResponseEntity<?> getBusinessUnits(
            @RequestParam("eid") String eid) {

        // EXPECTED: "EA1000/bussinessunits"
        String enterpriseId = eid.split("/")[0];

        return ResponseEntity.ok(
                (enterpriseId)
        );
    }

    // 🔹 Get Regions under Business Unit
    @GetMapping(params = {"eid", "buid"})
    public ResponseEntity<?> getRegions(
            @RequestParam("eid") String eid,
            @RequestParam("buid") String buid) {

        // eid = "EA1000/bussinessunits"
        // buid = "BU1000/regions"
        String enterpriseId = eid.split("/")[0];
        String businessUnitId = buid.split("/")[0];

        return ResponseEntity.ok(
                (businessUnitId)
        );
    }

    // 🔹 Get Countries under Region
    @GetMapping(params = {"eid", "buid", "rid"})
    public ResponseEntity<?> getCountries(
            @RequestParam("eid") String eid,
            @RequestParam("buid") String buid,
            @RequestParam("rid") String rid) {

        // eid = "EA1000/bussinessunits"
        // buid = "BU1000/regions"
        // rid = "RE1000/countries"

        String enterpriseId = eid.split("/")[0];
        String businessUnitId = buid.split("/")[0];
        String regionId = rid.split("/")[0];

        return ResponseEntity.ok
                (enterpriseId);

    }

}

