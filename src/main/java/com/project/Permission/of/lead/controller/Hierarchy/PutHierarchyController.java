package com.project.Permission.of.lead.controller.Hierarchy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.controller.CustomerController;
import com.project.Permission.of.lead.controller.LeadsController;
import com.project.Permission.of.lead.controller.PersonalManagementController;
import com.project.Permission.of.lead.dto.*;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.*;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/enterprises")
public class PutHierarchyController {


    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private BussinessUnitService bussinessUnitService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private StateService stateService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private TeritoryService teritoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PersonalManagementController personalManagementController;


    @Autowired
    private CustomerController customerController;

    @Autowired
    private LeadsController leadsController;



    private boolean hasRole(UserPrinciple userPrinciple,String role){
        return userPrinciple.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_" +role));
    }


    /// UPDATE ENTERPRISE..............................................................

    @PutMapping("/{eid}")

    private ResponseEntity<?>updateEnterprise(@RequestBody Map<String,Object> requestBody,
                                              @AuthenticationPrincipal UserPrinciple userPrinciple,
                                              @PathVariable String eid){

        Users loggedUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple, "ENTERPRISE_ADMIN") && !hasRole(userPrinciple,"BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires ENTERPRISE_ADMIN for update."), HttpStatus.FORBIDDEN);

        EnterpriseDto enterpriseDto = objectMapper.convertValue(requestBody, EnterpriseDto.class);

        EnterpriseDto dto = enterpriseService.updateEnterprise(eid, enterpriseDto, loggedUser);

        return ResponseEntity.ok(dto);

    }


    ///    CREATE BUSSINESS UNIT.........................................................

    @PutMapping("/{eid}/bussinessunits/{buid}")

    private ResponseEntity<?>updateBussinessUnit(@RequestBody BussinessUnitDto bussinessUnitDto,
                                              @AuthenticationPrincipal UserPrinciple userPrinciple,
                                              @PathVariable String eid,
                                              @PathVariable String buid){

        Users loggedUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple,"BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSSINESS ADMIN for update."), HttpStatus.FORBIDDEN);

//        BussinessUnitDto bussinessUnitDto=objectMapper.convertValue(requestBody, BussinessUnitDto.class);
        BussinessUnitDto update=bussinessUnitService.updateBussinessUnit(bussinessUnitDto,loggedUser,buid,eid);
        return ResponseEntity.status(HttpStatus.OK).body(update);

    }


    /// UPDATE REGIONS.............................................................


    @PutMapping("/{eid}/bussinessunits/{buid}/regions/{rid}")

    private ResponseEntity<?>updateRegion(@RequestBody RegionDto regionDto,
                                                 @AuthenticationPrincipal UserPrinciple userPrinciple,
                                                 @PathVariable String eid,
                                                 @PathVariable String buid,
                                                  @PathVariable String rid){

        Users loggedUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple,"BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSSINESS ADMIN for update region."), HttpStatus.FORBIDDEN);

//        RegionDto regionDto=objectMapper.convertValue(requestBody, RegionDto.class);

        RegionDto updated=regionService.updateRegion(regionDto,loggedUser,eid,buid,rid);
        return ResponseEntity.status(HttpStatus.OK).body(updated);

    }


    /// UPDATE COUNTRY................................................................................

    @PutMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}")

    private ResponseEntity<?>updateCountry(@RequestBody CountryDto countryDto,
                                           @AuthenticationPrincipal UserPrinciple userPrinciple,
                                           @PathVariable String eid,
                                           @PathVariable String buid,
                                           @PathVariable String rid,
                                           @PathVariable String cid){

        Users loggedUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple,"BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSSINESS ADMIN for update country."), HttpStatus.FORBIDDEN);

//        RegionDto regionDto=objectMapper.convertValue(requestBody, RegionDto.class);

        CountryDto countryDto1 = countryService.updateCountriesByHierarchyIds(countryDto, cid, eid, buid, rid, loggedUser);
        return ResponseEntity.status(HttpStatus.OK).body(countryDto1);



    }



    ///  UPDATE ZONES...........................................................................


    @PutMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}")

    private ResponseEntity<?>updateZone(@RequestBody ZoneDto zoneDto,
                                           @AuthenticationPrincipal UserPrinciple userPrinciple,
                                           @PathVariable String eid,
                                           @PathVariable String buid,
                                           @PathVariable String rid,
                                           @PathVariable String cid,
                                           @PathVariable String zid){

        Users loggedUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple,"BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSSINESS ADMIN for update zone."), HttpStatus.FORBIDDEN);

//        RegionDto regionDto=objectMapper.convertValue(requestBody, RegionDto.class);

        ZoneDto updated=zoneService.updateZones(zoneDto,loggedUser,zid,cid,rid,buid,eid);
        return ResponseEntity.status(HttpStatus.OK).body(updated);



    }


    ///  UPDATE STATE......................................................................................


    @PutMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states/{sid}")

    private ResponseEntity<?>updateState(@RequestBody StateDto stateDto,
                                        @AuthenticationPrincipal UserPrinciple userPrinciple,
                                        @PathVariable String eid,
                                        @PathVariable String buid,
                                        @PathVariable String rid,
                                        @PathVariable String cid,
                                        @PathVariable String zid,
                                        @PathVariable String sid){

        Users loggedUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple,"BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSSINESS ADMIN for update zone."), HttpStatus.FORBIDDEN);

//        RegionDto regionDto=objectMapper.convertValue(requestBody, RegionDto.class);

        StateDto update=stateService.updateState(stateDto,loggedUser,sid,zid,cid,rid,buid,eid);
        return ResponseEntity.status(HttpStatus.OK).body(update);



    }


    /// UPDATE DISTRICT...............................................................................

    @PutMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states/{sid}/districts/{did}")

    private ResponseEntity<?>updateDistrict(@RequestBody Map<String,Object> requestBody,
                                            @AuthenticationPrincipal UserPrinciple userPrinciple,
                                            @PathVariable String eid,
                                            @PathVariable String buid,
                                            @PathVariable String rid,
                                            @PathVariable String cid,
                                            @PathVariable String zid,
                                            @PathVariable String sid,
                                            @PathVariable String did){

        Users loggedUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple,"BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSSINESS ADMIN for update zone."), HttpStatus.FORBIDDEN);

//        RegionDto regionDto=objectMapper.convertValue(requestBody, RegionDto.class);

        DistrictDto districtDto=objectMapper.convertValue(requestBody,DistrictDto.class);
        DistrictDto updated=districtService.updateDistrict(districtDto,loggedUser,did,sid,zid,cid,rid,buid,eid);
        return ResponseEntity.ok().body(updated);



    }


    /// UPDATE TERRITORY....................................................................................

    @PutMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states/{sid}/districts/{did}/territories/{tid}")

    private ResponseEntity<?>updateDistrict(@RequestBody Map<String,Object> requestBody,
                                            @AuthenticationPrincipal UserPrinciple userPrinciple,
                                            @PathVariable String eid,
                                            @PathVariable String buid,
                                            @PathVariable String rid,
                                            @PathVariable String cid,
                                            @PathVariable String zid,
                                            @PathVariable String sid,
                                            @PathVariable String did,
                                            @PathVariable String tid){

        Users loggedUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple,"BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSSINESS ADMIN for update zone."), HttpStatus.FORBIDDEN);

//        RegionDto regionDto=objectMapper.convertValue(requestBody, RegionDto.class);

        TeritoryDto teritoryDto=objectMapper.convertValue(requestBody, TeritoryDto.class);

        TeritoryDto updated=teritoryService.updateTeritory(teritoryDto,loggedUser,tid,did,sid,zid,cid,rid,buid,eid);
        return ResponseEntity.status(HttpStatus.OK).body(updated);



    }


}
