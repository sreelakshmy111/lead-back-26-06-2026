package com.project.Permission.of.lead.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.dto.CustomerDto;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.CustomerService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enterprises")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    private boolean hasRole(UserPrinciple userPrinciple,String role){
        return userPrinciple.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }
//
//
//    @GetMapping(value = "/enterprise", params = "cid")
//    public ResponseEntity<?> getHierarchy(
//            @RequestParam("cid") String eidParams,
////            @RequestParam("buid") String bussiness,
//
////            @RequestBody(required = false) Map<String, Object> requiredBody,
//            @AuthenticationPrincipal UserPrinciple userPrinciple) {
//
//
//        String decodedEid= URLDecoder.decode(eidParams, StandardCharsets.UTF_8);
//        Users loggedInUser = userPrinciple.getUser();
//
//        if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
//            return ResponseEntity.ok("Only bussiness admin can get customers!");
//        }
//        if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/customer")){
//
//            // [EA1000/bussinessunits?buid=BU1000/customer]
//            String[] eSplits=decodedEid.split("/bussinessunits\\?buid=");  // [EA1000,BU1000/customer]
//            String enterpriseId=eSplits[0].trim();
//
//            String[] bSplits=eSplits[1].split("/customer"); // [BU1000]
//            String bussinessUnitId=bSplits[0].trim();
//
//            List<CustomerDto> customers=customerService.getCustomers(loggedInUser,enterpriseId,bussinessUnitId);
//            return ResponseEntity.ok(customers);
//        }
//        return ResponseEntity.ok("url not found for customer get");
//
//    }



///   CREATE CUSTOMER....................................................................................

  @PostMapping("/{eid}/bussinessunits/{buid}/customer_contact")
    public ResponseEntity<?> createCustomer( @RequestBody Map<String, Object> requestBody,
                                             @AuthenticationPrincipal  UserPrinciple userPrinciple,
                                             @PathVariable String eid,
                                             @PathVariable String buid) {

        Users loggedInUser=userPrinciple.getUser();

        if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only bussiness admin can create customers!");
        }

            CustomerDto customerDto=objectMapper.convertValue(requestBody,CustomerDto.class);
            CustomerDto createdCustomer=customerService.createCustomer(customerDto,eid,buid,loggedInUser);
            return ResponseEntity.ok(createdCustomer);
        }




    //..........get customers..............................................
    @GetMapping("{eid}/bussinessunits/{buid}/customer_contact")
    public ResponseEntity<?> getCustomers(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                          @PathVariable String eid,
                                          @PathVariable String buid) {

         Users loggedInUser=userPrinciple.getUser();

        if(!hasRole(userPrinciple,"BUSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER")){
            return ResponseEntity.ok("Only bussiness admin can get customers!");
        }


            List<CustomerDto> customers=customerService.getCustomers(loggedInUser,eid,buid);
            return ResponseEntity.ok(customers);
        }


/// UPDATE CUSTOMER CONTACT...........................................................................

    public ResponseEntity<?> updateCustomerContact(@RequestBody Map<String, Object> requestBody,
                                                   @AuthenticationPrincipal UserPrinciple userPrinciple,
                                                   @PathVariable String eid,
                                                   @PathVariable String buid,
                                                   @PathVariable String cuid) {

    Users loggedUser=userPrinciple.getUser();
    if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ONLY BUSINESS ADMIN CAN PERFORM THIS ACTION");
    }



        CustomerDto customerDto=objectMapper.convertValue(requestBody,CustomerDto.class);
        CustomerDto updateContact=customerService.updateContact(customerDto,loggedUser,eid,buid,cuid);
        return ResponseEntity.ok(updateContact);
    }

}
