package com.project.Permission.of.lead.controller.Hierarchy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.controller.CustomerController;
import com.project.Permission.of.lead.controller.LeadsController;
import com.project.Permission.of.lead.controller.PersonalManagementController;
import com.project.Permission.of.lead.controller.ProcuctCatalogue.ProductCatalogueController;
import com.project.Permission.of.lead.controller.ProcuctCatalogue.ServiceCatalougeController;
import com.project.Permission.of.lead.dto.*;
import com.project.Permission.of.lead.service.*;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enterprises")
public class GetHierachyController {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private BussinessUnitService bussinessUnitService;

    @Autowired
    private RegionService regionService;

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
    private ServiceCatalougeController serviceCatalougeController;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private LeadsController leadsController;


    private boolean hasRole(UserPrinciple userPrinciple, String role) {
        return userPrinciple.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

//.......get enterprise.................................................

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ENTERPRISE_ADMIN', 'BUSINESS_ADMIN')")
    public ResponseEntity<List<EnterpriseDto>> getAllEnterprise(
            @AuthenticationPrincipal UserPrinciple userPrinciple) {
        List<EnterpriseDto> e = enterpriseService.getAll();
        return ResponseEntity.ok(e);
    }


    //............get enterprise by eid..............................................
    @GetMapping("/{eid}")
    @PreAuthorize("hasRole('ENTERPRISE_ADMIN')")
    public ResponseEntity<EnterpriseDto> getEnterpriseById(@PathVariable String eid,
                                                           @AuthenticationPrincipal UserPrinciple userPrinciple) {
        EnterpriseDto dto = enterpriseService.getenterpriseByEid(eid);
        return ResponseEntity.ok(dto);
    }


//.....get bussinessuits.................................................................

    @GetMapping("/{eid}/bussinessunits")
    public ResponseEntity<?> getBusinessUnits(@PathVariable String eid,
                                              @AuthenticationPrincipal UserPrinciple userPrinciple) {
        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACESS DENIED -ONLY BUSSINESS ADMIN CAN ACCESS");
        }


        List<BussinessUnitDto> busniess = bussinessUnitService.getBussinessEnterpriseById(eid);
        return ResponseEntity.ok(busniess);


    }


    //......Get bussinessunit by buid..........................................................
    @GetMapping("/{eid}/bussinessunits/{buid}")
    public ResponseEntity<?> getBusinessUnits(@PathVariable String eid,
                                              @PathVariable String buid,
                                              @AuthenticationPrincipal UserPrinciple userPrinciple) {
        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "ENTERPRISE_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return new ResponseEntity<>(Map.of("error", "ACCESS DENIED :only BUSSINESS ADMIN CAN GET"), HttpStatus.FORBIDDEN);
        }

        BussinessUnitDto busniess = bussinessUnitService.getBussinessByIdEnterpriseById(eid,buid);
        return ResponseEntity.ok(busniess);

    }


    //.......get regionss.........................................................................
    @GetMapping("/{eid}/bussinessunits/{buid}/regions")
    public ResponseEntity<?>getRegions(@PathVariable String eid,
                                       @PathVariable String buid,
                                       @AuthenticationPrincipal UserPrinciple userPrinciple){

          if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
              return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
          }

          List<RegionDto> regions = regionService.getRegions(eid,buid);
          return ResponseEntity.ok(regions);

      }


    //.........get regions by rid...................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/regions/{rid}")
    public ResponseEntity<?>getRegionsByRid(@PathVariable String eid,
                                       @PathVariable String buid,
                                       @PathVariable String rid,
                                       @AuthenticationPrincipal UserPrinciple userPrinciple){

        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
        }

        RegionDto regions = regionService.getRegionById(eid,buid,rid);
        return ResponseEntity.ok(regions);

    }


    //....get countries...........................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries")
    public ResponseEntity<?>getCountries(@PathVariable String eid,
                                            @PathVariable String buid,
                                            @PathVariable String rid,
                                            @AuthenticationPrincipal UserPrinciple userPrinciple){

        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
        }

        List<CountryDto> countryDtos=countryService.getCoutries(eid,buid,rid);
        return ResponseEntity.ok(countryDtos);

    }


    //.........get countriess by cid........................

    @GetMapping("/{eid}/busssinessunits/{buid}/regions/{rid}/countries/{cid}")
    public ResponseEntity<?>getCountriesById(@PathVariable String eid,
                                         @PathVariable String buid,
                                         @PathVariable String rid,
                                         @PathVariable String cid,
                                         @AuthenticationPrincipal UserPrinciple userPrinciple){

        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
        }

        CountryDto countryDtos=countryService.getCoutriesById(eid,buid,rid,cid);
        return ResponseEntity.ok(countryDtos);

    }

    //.......get zones....................................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones")
    public ResponseEntity<?>getZones(@PathVariable String eid,
                                             @PathVariable String buid,
                                             @PathVariable String rid,
                                             @PathVariable String cid,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple){

        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
        }

        List<ZoneDto> zoneDto=zoneService.getZones(cid,rid,buid,eid);
        return ResponseEntity.status(HttpStatus.OK).body(zoneDto);

    }


    //......get zone by id.................................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}")
    public ResponseEntity<?>getZonesById(@PathVariable String eid,
                                     @PathVariable String buid,
                                     @PathVariable String rid,
                                     @PathVariable String cid,
                                     @PathVariable String zid,
                                     @AuthenticationPrincipal UserPrinciple userPrinciple){

        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
        }

        ZoneDto zoneDto=zoneService.getZonesById(cid,rid,buid,eid,zid);
        return ResponseEntity.status(HttpStatus.OK).body(zoneDto);

    }

    /// .......GET states....................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states")
    public ResponseEntity<?>getStates(@PathVariable String eid,
                                         @PathVariable String buid,
                                         @PathVariable String rid,
                                         @PathVariable String cid,
                                         @PathVariable String zid,
                                         @AuthenticationPrincipal UserPrinciple userPrinciple){

        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
        }


        List<StateDto> stateDtos=stateService.getStates(zid,cid,rid,buid,eid);
        return ResponseEntity.status(HttpStatus.OK).body(stateDtos);


    }


    /// .......GET states....................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states/{sid}")
    public ResponseEntity<?>getStatesById(@PathVariable String eid,
                                      @PathVariable String buid,
                                      @PathVariable String rid,
                                      @PathVariable String cid,
                                      @PathVariable String zid,
                                      @PathVariable String sid,
                                      @AuthenticationPrincipal UserPrinciple userPrinciple) {

        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
        }


        StateDto stateDtos = stateService.getStateById(zid, cid, rid, buid, eid, sid);
        return ResponseEntity.status(HttpStatus.OK).body(stateDtos);

    }
        // GET districts....................................................................


        @GetMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states/{sid}/districts")
        public ResponseEntity<?>getDistricts(@PathVariable String eid,
                @PathVariable String buid,
                @PathVariable String rid,
                @PathVariable String cid,
                @PathVariable String zid,
                @PathVariable String sid,
                @AuthenticationPrincipal UserPrinciple userPrinciple){

            if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
                return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
            }


            List<DistrictDto> districtDtos=districtService.getDistricts(sid,zid,cid,rid,buid,eid);
            return ResponseEntity.ok().body(districtDtos);



        }

    // GET districts By id...................................................................


    @GetMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states/{sid}/districts/{did}")
    public ResponseEntity<?>getDistrictsById(@PathVariable String eid,
                                         @PathVariable String buid,
                                         @PathVariable String rid,
                                         @PathVariable String cid,
                                         @PathVariable String zid,
                                         @PathVariable String sid,
                                         @PathVariable String did,
                                         @AuthenticationPrincipal UserPrinciple userPrinciple){

        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
        }


        DistrictDto districtDtos=districtService.getDistrictById(did,sid,zid,cid,rid,buid,eid);
        return ResponseEntity.ok().body(districtDtos);


    }


    // GET Territories ...................................................................


    @GetMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states/{sid}/districts/{did}/territories")
    public ResponseEntity<?>getTerritories(@PathVariable String eid,
                                             @PathVariable String buid,
                                             @PathVariable String rid,
                                             @PathVariable String cid,
                                             @PathVariable String zid,
                                             @PathVariable String sid,
                                             @PathVariable String did,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple){

        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
        }


        List<TeritoryDto> teritoryDtos=teritoryService.getTeritories(did,sid,zid,cid,rid,buid,eid);
        return ResponseEntity.ok(teritoryDtos);


    }


    // GET Territories ...................................................................


    @GetMapping("/{eid}/bussinessunits/{buid}/regions/{rid}/countries/{cid}/zones/{zid}/states/{sid}/districts/{did}/territories/{tid}")
    public ResponseEntity<?>getTerritoriesById(@PathVariable String eid,
                                           @PathVariable String buid,
                                           @PathVariable String rid,
                                           @PathVariable String cid,
                                           @PathVariable String zid,
                                           @PathVariable String sid,
                                           @PathVariable String did,
                                           @PathVariable String tid,
                                           @AuthenticationPrincipal UserPrinciple userPrinciple){

        if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
            return new ResponseEntity<>(Map.of("error", "ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
        }


        TeritoryDto teritoryDtos=teritoryService.getTeritorieById(did,sid,zid,cid,rid,buid,eid,tid);
        return ResponseEntity.ok(teritoryDtos);


    }




}