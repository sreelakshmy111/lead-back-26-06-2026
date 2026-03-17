package com.project.Permission.of.lead.controller.Enterprise;


import com.project.Permission.of.lead.dto.EnterpriseDto;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.EnterpriseService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

//@RestController

public class EnterpriseController {


    private EnterpriseDto  enterpriseDto;

    @Autowired
    private EnterpriseService enterpriseService;

//    update enterprises...................................................................................
//    @PreAuthorize("hasRole('ENTERPRISE_ADMIN')")\
//
//    @PutMapping("enterprisess")
//    @PreAuthorize("hasRole('ENTERPRISE_ADMIN')")
//    public ResponseEntity<EnterpriseDto> updateEnterprise(
//            @RequestParam("eid") Long id,
//            @RequestBody EnterpriseDto updateEnterpriseDto,
//            @AuthenticationPrincipal UserPrinciple userPrinciple){
//        Users loggedInUser = userPrinciple.getUser();
//        System.out.println("logged in user" +loggedInUser);
//
//
//
//        EnterpriseDto dto=enterpriseService.updateEnterprise(id,updateEnterpriseDto,loggedInUser);
//        return ResponseEntity.ok(dto);
//    }


//    create enterprise...............................................................................

//    @PostMapping("/enterprisess")
    @PreAuthorize("hasRole('ENTERPRISE_ADMIN')")
    public ResponseEntity<EnterpriseDto> createEnterprise(@RequestBody EnterpriseDto enterpriseDto,
                                                          @AuthenticationPrincipal UserPrinciple userPrinciple){


        Users loggedInUser = userPrinciple.getUser(); // get the Users entity from your UserPrinciple
        EnterpriseDto saved=enterpriseService.createEnterprise(enterpriseDto,loggedInUser);

       return ResponseEntity.ok(saved);

    }

//    @GetMapping("enterprises")
//    @PreAuthorize("hasAnyRole('ENTERPRISE_ADMIN', 'BUSINESS_ADMIN')")
//    public ResponseEntity<List<EnterpriseDto>> getAllEnterprise(){
//        List<EnterpriseDto> e=enterpriseService.getAll(loggedInUser);
//        return ResponseEntity.ok(e);
//    }
//
//
//    Get enterprises...................................................................................







//






}
