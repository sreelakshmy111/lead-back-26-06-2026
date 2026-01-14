package com.project.Permission.of.lead.controller.Hierarchy;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.controller.CustomerController;
import com.project.Permission.of.lead.controller.LeadsController;
import com.project.Permission.of.lead.controller.PersonalManagementController;
import com.project.Permission.of.lead.controller.ProcuctCatalogue.ProductCatalogueController;
import com.project.Permission.of.lead.controller.ProcuctCatalogue.ServiceCatalougeController;
import com.project.Permission.of.lead.dto.*;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.*;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/enterprises")
public class PostHierarchyController {


    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private BussinessUnitService bussinessUnitService;

    @Autowired
    private CountryService countryService;

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
    private PersonalManagementController personalManagementController;

    @Autowired
    private ProductCatalogueController productCatalogueController;

    @Autowired
    private ServiceCatalougeController serviceCatalogueController;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private LeadsController leadsController;

    private boolean hasRole(UserPrinciple userPrinciple,String role){
        return userPrinciple.getAuthorities().stream().
                anyMatch(a->a.getAuthority().equals("ROLE_" + role));
    }

    /// CREATE ENTERPRISE..................................................................

    @PostMapping("")

    public ResponseEntity<?> createEnterprise(@RequestBody EnterpriseDto enterpriseDto,

                                                   @AuthenticationPrincipal UserPrinciple userPrinciple){

        Users loggedInUser = userPrinciple.getUser(); // get the Users entity from your UserPrinciple

        if (!hasRole(userPrinciple, "ENTERPRISE_ADMIN") && !hasRole(userPrinciple,"BUSINESS_ADMIN"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACESS DENIED -ONLY BUSSINESS ADMIN CAN ACCESS");

        EnterpriseDto saved=enterpriseService.createEnterprise(enterpriseDto,loggedInUser);

        return ResponseEntity.ok(saved);

    }

    @PostMapping("/{eid}/bussinessunits")
    public ResponseEntity<?>createBussinessUnit(@RequestBody BussinessUnitDto bussinessUnitDto,
                                                @PathVariable String eid,
                                                @AuthenticationPrincipal UserPrinciple userPrinciple){

        Users loggedInUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple, "ENTERPRISE_ADMIN") && !hasRole(userPrinciple,"BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires ENTERPRISE_ADMIN for Create."), HttpStatus.FORBIDDEN);


        BussinessUnitDto createdBu = bussinessUnitService.createBussinessUnit(eid, bussinessUnitDto, loggedInUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBu);

    }


    /// CREATE REGIONSS.....................................................................


    @PostMapping("{eid}/bussinessunits/{buid}/regions")
    public ResponseEntity<?>createRegions(@RequestBody Map<String,Object> requestBody,
                                          @PathVariable String eid,
                                          @PathVariable String buid,
                                          @AuthenticationPrincipal UserPrinciple userPrinciple){



        Users loggedInUser=userPrinciple.getUser();
        if (!hasRole(userPrinciple, "BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSINESS_ADMIN for Create."), HttpStatus.FORBIDDEN);


        RegionDto regionDto = objectMapper.convertValue(requestBody, RegionDto.class);
        RegionDto createdRegion = regionService.createRegions(regionDto, eid, buid, loggedInUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRegion);


    }



    ///...CREATE COUNTRIES..............................................................................

    @PostMapping("{eid}/bussinessunits/{buid}/regions/{rid}/countries")
    public ResponseEntity<?>createCountries(@RequestBody Map<String,Object> requestBody,
                                          @PathVariable String eid,
                                          @PathVariable String buid,
                                          @PathVariable String rid,
                                          @AuthenticationPrincipal UserPrinciple userPrinciple){



        Users loggedInUser=userPrinciple.getUser();
        if (!hasRole(userPrinciple, "BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSINESS_ADMIN for Create."), HttpStatus.FORBIDDEN);


        CountryDto countryDto = objectMapper.convertValue(requestBody, CountryDto.class);

        CountryDto created = countryService.createCountry(countryDto, eid, buid, rid, loggedInUser);
        return ResponseEntity.ok(created);

    }


    /// CREATE ZONES..............................................................................

    @PostMapping("{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones")
    public ResponseEntity<?>createCountries(@RequestBody Map<String,Object> requestBody,
                                            @PathVariable String eid,
                                            @PathVariable String buid,
                                            @PathVariable String rid,
                                            @PathVariable String cid,
                                            @AuthenticationPrincipal UserPrinciple userPrinciple){



        Users loggedInUser=userPrinciple.getUser();
        if (!hasRole(userPrinciple, "BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSINESS_ADMIN for Create."), HttpStatus.FORBIDDEN);


        ZoneDto zoneDto = objectMapper.convertValue(requestBody, ZoneDto.class);

        ZoneDto created =zoneService.createZone(zoneDto,eid,buid,rid,cid,loggedInUser);
        return ResponseEntity.status(HttpStatus.OK).body(created);


    }


    ///  CREATE STATES.......................................................................


    @PostMapping("{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states")
    public ResponseEntity<?>createCountries(@RequestBody Map<String,Object> requestBody,
                                            @PathVariable String eid,
                                            @PathVariable String buid,
                                            @PathVariable String rid,
                                            @PathVariable String cid,
                                            @PathVariable String zid,
                                            @AuthenticationPrincipal UserPrinciple userPrinciple){



        Users loggedInUser=userPrinciple.getUser();
        if (!hasRole(userPrinciple, "BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSINESS_ADMIN for Create."), HttpStatus.FORBIDDEN);


        StateDto stateDto=objectMapper.convertValue(requestBody,StateDto.class);

        StateDto savedState=stateService.createState(stateDto,loggedInUser,zid,cid,rid,buid,eid);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedState);


    }


    /// CREATE DISTRICTS...................................................................

    @PostMapping("{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states/{sid}/districts")
    public ResponseEntity<?>createCountries(@RequestBody Map<String,Object> requestBody,
                                            @PathVariable String eid,
                                            @PathVariable String buid,
                                            @PathVariable String rid,
                                            @PathVariable String cid,
                                            @PathVariable String zid,
                                            @PathVariable String sid,
                                            @AuthenticationPrincipal UserPrinciple userPrinciple){



        Users loggedInUser=userPrinciple.getUser();
        if (!hasRole(userPrinciple, "BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSINESS_ADMIN for Create."), HttpStatus.FORBIDDEN);



        DistrictDto districtDto=objectMapper.convertValue(requestBody,DistrictDto.class);
        DistrictDto saveDistrict=districtService.createDistrict(districtDto,loggedInUser,sid,zid,cid,rid,buid,eid);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(saveDistrict);


    }



    /// CREATE TERRITORIES...................................................................

    @PostMapping("{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states/{sid}/districts/{did}/territories")
    public ResponseEntity<?>createTerritories(@RequestBody Map<String,Object> requestBody,
                                            @PathVariable String eid,
                                            @PathVariable String buid,
                                            @PathVariable String rid,
                                            @PathVariable String cid,
                                            @PathVariable String zid,
                                            @PathVariable String sid,
                                            @PathVariable String did,
                                            @AuthenticationPrincipal UserPrinciple userPrinciple){



        Users loggedInUser=userPrinciple.getUser();
        if (!hasRole(userPrinciple, "BUSINESS_ADMIN"))
            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSINESS_ADMIN for Create."), HttpStatus.FORBIDDEN);



        TeritoryDto teritoryDto=objectMapper.convertValue(requestBody, TeritoryDto.class);
        TeritoryDto saveTeritory=teritoryService.createTeritory(teritoryDto,loggedInUser,did,sid,zid,cid,rid,buid,eid);
        return ResponseEntity.ok().body(saveTeritory);


    }


}
