package com.project.Permission.of.lead.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.dto.BussinessUnitDto;
import com.project.Permission.of.lead.dto.RegionDto;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.BussinessUnitService;
import com.project.Permission.of.lead.service.RegionService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enterprises")
public class BussiessUnitController {

    @Autowired
    private BussinessUnitService bussinessUnitService;

    @Autowired
    private RegionService regionService;


    //     You would need to inject CountryService, ZoneService, and ObjectMapper
    @Autowired
    private ObjectMapper objectMapper;

    // Helper method for manual role check (Crucial for granular security within the router)
    private boolean hasRole(UserPrinciple userPrinciple, String role) {
        // Assuming roles are stored as 'ROLE_NAME'
        return userPrinciple.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }



    //........get bussiness unitssss................................


//    @GetMapping("/bussinessunits")
    public ResponseEntity<?> getBusinessUnits(@RequestParam String eid) {

        if (eid.endsWith("/bussinessunits")) {
            String enterpriseId = eid.split("/")[0];
            return ResponseEntity.ok(enterpriseId);
        }



        return ResponseEntity.ok("get enterprise id");
    }




    // --------------------------------------------------------------------------------------
    // THE CENTRALIZED HIERARCHICAL CRUD ROUTER
    // Maps: /enterprises?eid={complex/path/string}
    // --------------------------------------------------------------------------------------
//
//    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, params = "eid")
//    @PreAuthorize("hasAnyRole('ENTERPRISE_ADMIN', 'BUSINESS_ADMIN', 'READ_ONLY')")
//    public ResponseEntity<?> handleHierarchicalCRUD(
//            @RequestParam("eid") String eidParam,
//            @RequestBody(required = false) Map<String, Object> requestBody,
//            @AuthenticationPrincipal UserPrinciple userPrinciple,
//            HttpMethod httpMethod) {
//
//        try {
//            String decodedEid = URLDecoder.decode(eidParam, StandardCharsets.UTF_8);
//            Users loggedInUser = userPrinciple.getUser();

//            // -----------------------------------------------------------
//            // 1. REGION LOGIC (Nested Path: .../regions) - MOST SPECIFIC PATH FIRST
//            // -----------------------------------------------------------


//            if (decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions")) {
//
//                // 1. Parsing Region IDs
//                String[] enterpriseSplit = decodedEid.split("/bussinessunits\\?buid=");
//                Long enterpriseId = Long.parseLong(enterpriseSplit[0].trim());
//                String[] buSplit = enterpriseSplit[1].split("/regions");
//                Long businessUnitId = Long.parseLong(buSplit[0].trim());
//
//                // 2. Security and Routing based on Method
//                if (httpMethod == HttpMethod.POST) { // CREATE 🟢
//                    if (!hasRole(userPrinciple, "BUSINESS_ADMIN"))
//                        return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSINESS_ADMIN for Create."), HttpStatus.FORBIDDEN);
//
//                    RegionDto dto = objectMapper.convertValue(requestBody, RegionDto.class);
//                    RegionDto createdRegion = regionService.createRegions(dto, enterpriseId, businessUnitId, loggedInUser);
//                    return ResponseEntity.status(HttpStatus.CREATED).body(createdRegion);
//
//                }
//                if (httpMethod == HttpMethod.PUT) { // UPDATE 🟡
//                        if (!hasRole(userPrinciple, "BUSINESS_ADMIN"))
//                            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSINESS_ADMIN for Update."), HttpStatus.FORBIDDEN);
//
//                        RegionDto dto = objectMapper.convertValue(requestBody, RegionDto.class);
//                        Long regionId = dto.getId(); // ID must be in the body
//                        if (regionId == null) return ResponseEntity.badRequest().body(Map.of("error", "Region ID required in body for update."));
//
//                        RegionDto updatedRegion = regionService.updateRegion(dto, enterpriseId, businessUnitId, loggedInUser);
//                        return ResponseEntity.ok(updatedRegion);
//
//                    } else if (httpMethod == HttpMethod.GET) { // READ 🔵
//                        List<RegionDto> regions = regionService.getAllRegionsByBusinessUnit(businessUnitId);
//                        return ResponseEntity.ok(regions);
//                    }
            }


            // -----------------------------------------------------------
            // 2. BUSINESS UNIT LOGIC (Simplest Path: .../bussinessunits)
            // -----------------------------------------------------------


//            else if (decodedEid.contains("/bussinessunits")) {
//
//                // 1. Parsing BU ID
//                String[] parts = decodedEid.split("/bussinessunits");
//                if (parts.length > 1 && !parts[1].trim().isEmpty())
//                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid path segment after /bussinessunits."));
//                Long enterpriseId = Long.parseLong(parts[0].trim());
//
//                // 2. Security and Routing based on Method
//                if (httpMethod == HttpMethod.POST) { // CREATE 🟢
//                    if (!hasRole(userPrinciple, "ENTERPRISE_ADMIN"))
//                        return new ResponseEntity<>(Map.of("error", "Access Denied: Requires ENTERPRISE_ADMIN for Create."), HttpStatus.FORBIDDEN);
//
//                    BussinessUnitDto dto = objectMapper.convertValue(requestBody, BussinessUnitDto.class);
//                    BussinessUnitDto createdBu = bussinessUnitService.createBussinessUnit(enterpriseId, dto, loggedInUser);
//                    return ResponseEntity.status(HttpStatus.CREATED).body(createdBu);
//
//

//                }
//                    else if (httpMethod == HttpMethod.PUT) { // UPDATE 🟡
//                        if (!hasRole(userPrinciple, "ENTERPRISE_ADMIN"))
//                            return new ResponseEntity<>(Map.of("error", "Access Denied: Requires ENTERPRISE_ADMIN for Update."), HttpStatus.FORBIDDEN);
//
//                        BussinessUnitDto dto = objectMapper.convertValue(requestBody, BussinessUnitDto.class);
//                        Long buId = dto.getId(); // ID must be in the body
//                        if (buId == null) return ResponseEntity.badRequest().body(Map.of("error", "BU ID required in body for update."));
//
//                        BussinessUnitDto updatedBu = bussinessUnitService.updateBussinessEnterprise(enterpriseId, buId, dto, loggedInUser);
//                        return ResponseEntity.ok(updatedBu);

//                    } else if (httpMethod == HttpMethod.GET) { // READ 🔵
//                        List<BussinessUnitDto> units = bussinessUnitService.getAllBussinessUnitsByEnterprise(enterpriseId);
//                        return ResponseEntity.ok(units);
////                    }
//                }
//                // Add logic blocks for COUNTRY, ZONE, etc., here...
//            }
//                return ResponseEntity.badRequest().body(Map.of("error", "Invalid or unrecognized URL path format."));
//
//        }
//          catch (NumberFormatException e) {
//            return ResponseEntity.badRequest().body(Map.of("error", "Invalid ID format in the URL path."));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body(Map.of("error", "Processing error: " + e.getMessage()));
//        }
//        // If the resource was hit but the HTTP method wasn't handled (e.g., DELETE),
//        // the method implicitly returns 405 Method Not Allowed due to the @RequestMapping constraints.
//
//    }
//}


































































//    create bussiness unit................................


//@PostMapping(params = {"!buid","eid" })
//@PreAuthorize("hasRole('ENTERPRISE_ADMIN')")
//public ResponseEntity<BussinessUnitDto> createBussinessUnit(
//        @RequestParam("eid") String eidParam,
//
//        @RequestBody BussinessUnitDto bussinessUnitDto,
//        @AuthenticationPrincipal UserPrinciple userPrinciple) {
//
//
//
//    try {
//        // Expected format: id=1/bussinessunits
//        String[] parts = eidParam.split("/bussinessunits");
//        if (parts.length == 0 || parts[0].trim().isEmpty()) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//
//        Long enterpriseId = Long.parseLong(parts[0].trim());
//
//        // ✅ Set enterpriseId for service/mapper
//        bussinessUnitDto.setEnterpriseId(enterpriseId);
//
//        Users loggedInUser = userPrinciple.getUser();
//        BussinessUnitDto savedUnit = bussinessUnitService.createBussinessUnit(enterpriseId, bussinessUnitDto, loggedInUser);
//
//        return new ResponseEntity<>(savedUnit, HttpStatus.CREATED);
//
//    } catch (NumberFormatException e) {
//        System.err.println("Invalid number format in idParam: " + eidParam);
//        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//    } catch (Exception e) {
//        e.printStackTrace();
//        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}


//// correct api for region creationsss..............................................................
//@PostMapping( params = {"eid", "buid"})
//@PreAuthorize("hasRole('BUSINESS_ADMIN')")
////    @PreAuthorize("permitAll()")
//public ResponseEntity<?> createRegion(
//        @RequestParam("eid") String eidParam,
//        @RequestParam("buid") String buidMarker,
//        @RequestBody RegionDto regionDto,
//        @AuthenticationPrincipal UserPrinciple userPrinciple) {
//
//
//    // 🔍 Debug line — check what roles & permissions this user really has
//    System.out.println("Logged in user: " + userPrinciple.getUsername());
//    System.out.println("Authorities: " + userPrinciple.getAuthorities());
//    System.out.println("Buid Marker (Routing Check): " + buidMarker);
//
//    try {
//        // Example URL: /enterprise?eid=1/bussinessunits?buid=4/regions
//        System.out.println("Raw eidParam = " + eidParam);
//
//        String decodedEid = URLDecoder.decode(eidParam, StandardCharsets.UTF_8);
//
//        // ✅ Validate expected format
//        if (!decodedEid.contains("/bussinessunits?buid=") || !decodedEid.contains("/regions")) {
//            return ResponseEntity.badRequest().body(
//                    Map.of("error", "Invalid format. Expected eid={enterpriseId}/bussinessunits?buid={businessUnitId}/regions")
//            );
//        }
//
//        // ✅ Extract IDs
//        String[] enterpriseSplit = decodedEid.split("/bussinessunits\\?buid=");
//        Long enterpriseId = Long.parseLong(enterpriseSplit[0].trim());
//
//        String[] buSplit = enterpriseSplit[1].split("/regions");
//        Long businessUnitId = Long.parseLong(buSplit[0].trim());
//
//        Users loggedInUser = userPrinciple.getUser();
//        RegionDto createdRegion = regionService.createRegions(regionDto, enterpriseId, businessUnitId, loggedInUser);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdRegion);
//
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        return ResponseEntity.badRequest().body("Invalid format for eid param");
//    }
//}
//



//    @PostMapping(params = "eid")
//    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
//    public ResponseEntity<RegionDto> createRegion(
//            @RequestParam("eid") String eidParam,
////            @RequestParam("buid") String buidParam,
//            @RequestBody RegionDto regionDto,
//            @AuthenticationPrincipal UserPrinciple userPrinciple) {
//
//        try {
//            Long enterpriseId = Long.parseLong(eidParam.split("/bussinessunits")[0].trim());
//            Long businessUnitId = Long.parseLong(buidParam.split("/regions")[0].trim());
//
//            Users loggedInUser = userPrinciple.getUser();
//            RegionDto savedRegion = regionService.createRegions(
//                    regionDto, enterpriseId, businessUnitId, loggedInUser
//            );
//
//            return new ResponseEntity<>(savedRegion, HttpStatus.CREATED);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }





    //    create region under busines unit..............................................................


//    @PostMapping("/bussinessunits/regions")
//    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
//    public ResponseEntity<RegionDto> createRegion(
//            @RequestBody RegionDto regionDto,
//            @RequestParam("eid") Long enterpriseId,
//            @RequestParam("buid") Long businessUnitId,
//            @AuthenticationPrincipal UserPrinciple userPrinciple) {
//
//        System.out.println("Logged in user authorities: " + userPrinciple.getAuthorities());
//        Users loggedInUser = userPrinciple.getUser();
//
//        RegionDto savedRegion = regionService.createRegions(regionDto, enterpriseId, businessUnitId, loggedInUser);
//        return ResponseEntity.ok(savedRegion);
//
//    }


//        try {
//            // Remove trailing "/regions" if present
//            String cleaned = eidParam.replace("/regions", "");
//
//            // Split by "/bussinessunits?buid="
//            String[] parts = cleaned.split("/bussinessunits\\?buid=");
//            if (parts.length != 2) {
//                throw new RuntimeException(
//                        "Bad Request: Expected format /enterprises?eid={id}/bussinessunits?buid={id}/regions"
//                );
//            }
//
//            Long enterpriseId = Long.parseLong(parts[0]);
//            Long businessUnitId = Long.parseLong(parts[1]);
//
//            Users loggedInUser = userPrinciple.getUser();
//            RegionDto savedRegion = regionService.createRegions(regionDto, enterpriseId, businessUnitId, loggedInUser);
//
//            return ResponseEntity.ok(savedRegion);
//
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }







//update bussiness unit..........................................................


//   @ PreAuthorize("hasRole('BUSINESS_ADMIN')")
//   @PutMapping("/{enterpriseId}/bussinessunits/{businessunitId}")
//    public ResponseEntity<BussinessUnitDto> updateBussinessEnterprise(@PathVariable Long enterpriseId,
//                                                                      @PathVariable Long businessunitId,
//                                                                      @RequestBody BussinessUnitDto bussinessUnitDto,
//                                                                      @AuthenticationPrincipal UserPrinciple userPrinciple)
//   {
//
//       Users currentUser=userPrinciple.getUser();
//       BussinessUnitDto bussinessUnitDto1 =bussinessUnitService.updateBussinessEnterprise( enterpriseId,businessunitId, bussinessUnitDto,currentUser);
//    return ResponseEntity.ok(bussinessUnitDto1);
//
//
//
//    }




















//
//    @GetMapping("/bussinessunits")
//    public ResponseEntity<List<BussinessUnitDto>> getAll(){
//    List<BussinessUnitDto> bu=bussinessUnitService.getAll();
//    return ResponseEntity.ok(bu);
//    }
//
//
//    @GetMapping("/bussinessunits/{id}")
//    public ResponseEntity<BussinessUnitDto> getBussinessEnterpriseById(@PathVariable Long id){
//    BussinessUnitDto dto=bussinessUnitService.getBussinessEnterpriseById(id);
//    return ResponseEntity.ok(dto);
//
//    }







