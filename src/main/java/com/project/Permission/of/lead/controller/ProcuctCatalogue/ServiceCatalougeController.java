package com.project.Permission.of.lead.controller.ProcuctCatalogue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.dto.ProductCatalogueDto;
import com.project.Permission.of.lead.dto.ServiceCatalogueDto;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.BussinessUnitService;
import com.project.Permission.of.lead.service.ServiceCatalogueService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enterprises")
public class ServiceCatalougeController {

    @Autowired
    private ServiceCatalogueService serviceCatalogueService;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BussinessUnitService bussinessUnitService;

    private boolean hasRole(UserPrinciple userPrinciple, String role) {
        return userPrinciple.getAuthorities().stream().
                anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }


    /// get service catalogue table.......................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/service_catalogue")
    public ResponseEntity<?> getFullServiceCatalogue(
            @PathVariable String eid,
            @PathVariable String buid,
            @AuthenticationPrincipal UserPrinciple userPrinciple) {

        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") &&
                !hasRole(userPrinciple, "HR MANAGER") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        bussinessUnitService.validBuAccess(userPrinciple, buid);
        List<ServiceCatalogueDto> catalogue =
        serviceCatalogueService.getFullCatalogue(eid, buid);

        return ResponseEntity.ok(catalogue);
    }


    ///  CREATE SERVICE GROUP.................................................................................

    @PostMapping("{eid}/bussinessunits/{buid}/service_group")
    public ResponseEntity<?> createServiceGroup( @RequestBody ServiceCatalogueDto serviceCatalogueDto1,
                                                 @PathVariable String eid,
                                                 @PathVariable String buid,
                                                 @AuthenticationPrincipal  UserPrinciple userPrinciple) {

        Users loggedInUser =userPrinciple.getUser();
        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ONLY HR MANAGER CAN CREATE PRODUCT");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
         ServiceCatalogueDto serviceGroup = serviceCatalogueService.createServiceGroup(serviceCatalogueDto1, eid, buid, loggedInUser);

         return ResponseEntity.ok(serviceGroup);
        }


    ///  CREATE SERVICE TYPE.................................................................................

    @PostMapping("{eid}/bussinessunits/{buid}/service_group/{sgid}/service_type")
    public ResponseEntity<?> createServiceType(@PathVariable String eid,
                                               @PathVariable String buid,
                                               @PathVariable String sgid,
                                               @RequestBody ServiceCatalogueDto serviceCatalogueDto,
                                               @AuthenticationPrincipal UserPrinciple userPrinciple) {

        Users loggedInUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ONLY BUSSINESS ADMIN CAN CREATE SERVICE TYPE");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
            ServiceCatalogueDto serviceGroup = serviceCatalogueService.createServiceType(serviceCatalogueDto, eid, buid, sgid, loggedInUser);

            return ResponseEntity.ok(serviceGroup);
        }



    ///  CREATE SERVICE ITEM.........................................................................................

    @PostMapping("{eid}/bussinessunits/{buid}/service_group/{sgid}/service_type/{stid}/service_item")
    public ResponseEntity<?> createServiceItem(@PathVariable String eid,
                                               @PathVariable String buid,
                                               @PathVariable String sgid,
                                               @PathVariable String stid,
                                               @RequestBody ServiceCatalogueDto serviceCatalogueDto,
                                               @AuthenticationPrincipal  UserPrinciple userPrinciple) {

        Users loggedInUser=userPrinciple.getUser();
        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ONLY BUSSINESS ADMIN CAN CREATE SERVICE TYPE");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
        ServiceCatalogueDto serviceGroup = serviceCatalogueService.createServiceItem(serviceCatalogueDto, eid, buid, sgid, stid, loggedInUser);

        return ResponseEntity.ok(serviceGroup);

        }


    //......get service group...........................................................................

    @GetMapping("{eid}/bussinessunits/{buid}/service_group")
    public ResponseEntity<?> getServiceGroup(@PathVariable String eid,
                                             @PathVariable String buid,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple) {

        Users loggedInUser = userPrinciple.getUser();

        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.ok("ONLY BUSINESS ADMIN CAN GET SERVICE TYPE");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
        List<ServiceCatalogueDto> serviceGroup = serviceCatalogueService.getServiceGroups(eid, buid, loggedInUser);

        return ResponseEntity.ok(serviceGroup);
    }


    //............get service types....................................................
    @GetMapping("{eid}/bussinessunits/{buid}/service_group/{sgid}/service_type")
    public ResponseEntity<?> getServiceTypes(@PathVariable String eid,
                                             @PathVariable String buid,
                                             @PathVariable String sgid,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple) {

        Users loggedInUser = userPrinciple.getUser();

        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.ok("ONLY BUSINESS ADMIN CAN GET SERVICE TYPE");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
        List<ServiceCatalogueDto> serviceTypes = serviceCatalogueService.getServiceTypes(eid, buid, sgid, loggedInUser);
        return ResponseEntity.ok(serviceTypes);
    }


    /// ...........get service items....................................................

    @GetMapping("{eid}/bussinessunits/{buid}/service_group/{sgid}/service_type/{stid}/service_item")
    public ResponseEntity<?> getServiceItems(
            @PathVariable String eid,
            @PathVariable String buid,
            @PathVariable String sgid,
            @PathVariable String stid,
            @AuthenticationPrincipal UserPrinciple userPrinciple) {

        Users loggedInUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.ok("ONLY BUSINESS ADMIN CAN GET SERVICE TYPE");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
        List<ServiceCatalogueDto> ServiceItems = serviceCatalogueService.getServiceItems(eid, buid, sgid, stid, loggedInUser);
        return ResponseEntity.ok(ServiceItems);
    }
}