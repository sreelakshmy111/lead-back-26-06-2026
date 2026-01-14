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
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
//@RequestMapping("/territories")

public class TeritoryController {

    @Autowired
    private TeritoryService teritoryService;

    private boolean hasRole(UserPrinciple userPrinciple,String role){
        return userPrinciple.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }
    public ResponseEntity<?> getTerritories(String decodedEid, UserPrinciple userPrinciple, Users loggedInUser) {


        if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
          return ResponseEntity.ok("only bussiness can accesss the territories under eid and uid");
      }

      if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/territories")){
          //[EA1000,bussinessunits?buid=BU1000/territories]

          String[] eSplits=decodedEid.split("/bussinessunits\\?buid=");
          String enterpriseId=eSplits[0].trim();
          String[] bSplits=eSplits[1].split("/territories");
          String bussinessUnitId=bSplits[0].trim();

          List<TeritoryDto> terittories=teritoryService.getTerritorriesByBuidAndEid(enterpriseId,bussinessUnitId,userPrinciple,loggedInUser);
          return ResponseEntity.ok(terittories);
      }
        return  ResponseEntity.ok("error in url for territories get under buid and eid");

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
