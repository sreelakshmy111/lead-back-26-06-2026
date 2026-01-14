package com.project.Permission.of.lead.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.dto.PersonalManagementDto;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.mapper.PearsonalMapper;
import com.project.Permission.of.lead.service.PersonalManagementService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/enterprises")
@RestController

public class PersonalManagementController {

    @Autowired
    private PersonalManagementService personalManagementService;

    @Autowired
    private ObjectMapper objectMapper;


    private boolean hasRole(UserPrinciple userPrinciple, String role) {
        return userPrinciple.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }



    //...........getting all employees under eid......................................


    @GetMapping("{eid}/employees")
    public ResponseEntity<?> getallEmployee(@PathVariable String eid,
                                            @AuthenticationPrincipal UserPrinciple userPrinciple) {


            if (!hasRole(userPrinciple, "HR MANAGER")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED ONLY FOR HR MANAGER ROLE");
            }

            List<PersonalManagementDto> personalManagementDtos = personalManagementService.getAllEmployee(eid);
            return ResponseEntity.ok(personalManagementDtos);
         }



     // .........get employeees by eid and buid................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/employees")
    public ResponseEntity<?> getallEmployees(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                             @PathVariable String eid,
                                             @PathVariable String buid) {

         Users loggedInUser=userPrinciple.getUser();

            if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("ACCESS DENIED ONLY FOR BUSSINESS ADMIN");
            }

            List<PersonalManagementDto> getEmployee = personalManagementService.getEmployeeByEidAndBuid(loggedInUser, eid, buid);
            return ResponseEntity.ok(getEmployee);

        }



         // .........get employeees by eid and buid........................................................

//    @GetMapping("{eid}/bussinessunits/{buid}/employees")
//    public ResponseEntity<?> getEmployeesByPath(
//            @RequestParam("eid") String rawEid, @AuthenticationPrincipal UserPrinciple userPrinciple) {
//
//                    // 1. THIS WILL NOW PRINT
//        System.out.println(">>> API HIT SUCCESSFUL <<<");
//        System.out.println("RAW INPUT: " + rawEid);
//
//        return ResponseEntity.ok(personalManagementService.getEmployeeByEidAndBuid(userPrinciple.getUser(), enterpriseId, businessUnitId));
//
//
//
//    }





    @PostMapping("{eid}/employee")
    public ResponseEntity<?> createEmployee(@PathVariable String eid,
                                            @RequestBody Map<String, Object> requestBody,
                                            @AuthenticationPrincipal UserPrinciple userPrinciple) {

        Users loggedInUser = userPrinciple.getUser();


        if (!hasRole(userPrinciple, "HR MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED ONLY FOR HR MANAGER ROLE");
        }


        PersonalManagementDto personalManagementDto = objectMapper.convertValue(requestBody, PersonalManagementDto.class);
        PersonalManagementDto createdEmployee = personalManagementService.createEmployee(personalManagementDto, loggedInUser, eid);
        return ResponseEntity.ok(createdEmployee);
        }



//..............update employeee by id........................................................................


    @PutMapping("{eid}/employee/{empid}")
    public ResponseEntity<?> updateEmployeeById(@PathVariable String eid,
                                                 @PathVariable String empid,
                                                 @RequestBody Map<String, Object> requestBody,
                                                 @AuthenticationPrincipal UserPrinciple userPrinciple) {

        Users loggedUser=userPrinciple.getUser();
        if (!hasRole(userPrinciple, "HR MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("ACCESS DENIED ONLY FOR HR MANAGER ROLE");
        }

        PersonalManagementDto personalManagementDto = objectMapper.convertValue(requestBody, PersonalManagementDto.class);
        PersonalManagementDto createdEmployee = personalManagementService.updateEmployeeById(personalManagementDto, loggedUser, eid, empid);
        return ResponseEntity.ok(createdEmployee);

    }


    //.........assign bussiness unitss.....................................................

    @PutMapping("/{eid}/employee/{empid}/bussinessunit/{buid}")
    public ResponseEntity<?> assignBussinessUnit( @PathVariable String eid,
                                                  @PathVariable String buid,
                                                  @PathVariable String empid,
                                                  @RequestBody Map<String, Object> requestBody,
                                                  @AuthenticationPrincipal UserPrinciple userPrinciple) {


        if (!hasRole(userPrinciple, "HR MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ONLY HR MANAGER CAN ACCESS");
        }

        PersonalManagementDto personalManagementDto = objectMapper.convertValue(requestBody, PersonalManagementDto.class);
        PersonalManagementDto assignBussinessunit = personalManagementService.asssignBussinessUnit(personalManagementDto, eid, empid, buid);
        return ResponseEntity.ok(assignBussinessunit);
    }




    ///  assign  territoty to employee................................................................

  @PutMapping("{eid}/employee/{empid}/territories")
    public ResponseEntity<?> assignTerritories(@RequestBody Map<String, Object> requestBody,
                                              @PathVariable String eid,
                                              @PathVariable String empid,

                                              @AuthenticationPrincipal UserPrinciple userPrinciple) {
        Users loggedUser=userPrinciple.getUser();

        if (!hasRole(userPrinciple, "HR MANAGER")) {
            return ResponseEntity.ok("only HR MANAGER can assign territories to employees");
        }
        //[EA1000/employee?empid=EMP100/territory?tid=TE100]


        List<String> territoriesIds = (List<String>) requestBody.get("territories");
        if (territoriesIds == null || territoriesIds.isEmpty()) {
            return ResponseEntity.badRequest().body("No territories provided");
        }

        // 4️⃣ Convert territory IDs to DTOs using your mapper
        List<PersonalManagementDto> personalManagementDto = territoriesIds.stream()
                .map(tid -> PearsonalMapper.toDto(empid, eid, tid))
                .collect(Collectors.toList());

        // 5️⃣ Call service to assign territories
        List<PersonalManagementDto> assignedTerritories = personalManagementService.assignTerritory(
                personalManagementDto, loggedUser, eid, empid, territoriesIds);

        // 6️⃣ Return response
        return ResponseEntity.ok(assignedTerritories);


    }


    //.................Get employyes under territoryyy.................................

    @GetMapping("/{eid}/bussinessunits/{buid}/territory/{tid}/employees")
    public ResponseEntity<?> getEmployeesUnderTerritory(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                        @PathVariable String eid,
                                                        @PathVariable String buid,
                                                        @PathVariable String tid) {

            Users loggedInUser=userPrinciple.getUser();
                if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("ACCESS DENIED ONLY FOR BUSSINESS ADMIN");
                }
                // [EA1000/bussinessunits?buid=BU1000/territory?tid=TE100/employee]

                System.out.println(eid);
                System.out.println(buid);
                System.out.println(tid);

                List<PersonalManagementDto> getEmployee = personalManagementService.getEmployeeByEidAndBuidAndTid(loggedInUser, eid, buid, tid);
                return ResponseEntity.ok(getEmployee);

               }





}