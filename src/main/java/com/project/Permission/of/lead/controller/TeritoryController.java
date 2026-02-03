package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.LeadsDto;
import com.project.Permission.of.lead.dto.TeritoryDto;
import com.project.Permission.of.lead.entity.Teritory;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.TeritoryService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
@RequestMapping("/enterprises")

public class TeritoryController {

    @Autowired
    private TeritoryService teritoryService;

    private boolean hasRole(UserPrinciple userPrinciple,String role){
        return userPrinciple.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }


    @GetMapping("{eid}/bussinessunits/{buid}/territories")
    public ResponseEntity<?> getTerritories(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                            @PathVariable String enterpriseId,
                                            @PathVariable String bussinessUnitId

                                            ) {


        Users loggedInUser=userPrinciple.getUser();
        if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
          return ResponseEntity.ok("only bussiness can accesss the territories under eid and uid");
      }

        List<TeritoryDto> terittories=teritoryService.getTerritorriesByBuidAndEid(enterpriseId,bussinessUnitId,userPrinciple,loggedInUser);
          return ResponseEntity.ok(terittories);
      }


//    @Autowired
//    private TeritoryService teritoryService;
//
//    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
//    @PostMapping("")
//    public ResponseEntity<TeritoryDto> createTeritory(@RequestBody TeritoryDto teritoryDto) {
//        TeritoryDto teritoryDto1=teritoryService.createTeritory(teritoryDto);
//        return ResponseEntity.ok().body(teritoryDto1);
//
//    }
//
//    @GetMapping("")
//    public ResponseEntity<List<TeritoryDto>> getAll(){
//        List<TeritoryDto> teritory=teritoryService.getAll();
//        return ResponseEntity.ok().body(teritory);
//    }
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<TeritoryDto> getTeritoryById(@PathVariable Long id){
//        TeritoryDto dto=teritoryService.getTeritoryById(id);
//        return  ResponseEntity.ok().body(dto);
//    }

}
