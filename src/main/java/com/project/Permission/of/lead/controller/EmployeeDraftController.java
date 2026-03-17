package com.project.Permission.of.lead.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.dto.EmployeeDraftDto;
import com.project.Permission.of.lead.dto.LoginResponseDto;
import com.project.Permission.of.lead.dto.PersonalManagementDto;
import com.project.Permission.of.lead.dto.RegisterResponseDto;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.EmployeeDraftService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class EmployeeDraftController {


    @Autowired
    private EmployeeDraftService employeeDraftService;

    @Autowired
    private ObjectMapper objectMapper;

    private boolean hasRole(UserPrinciple userPrinciple, String role) {
        return userPrinciple.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }

    /// CREATE EMPLOYEEE AFTER THE REGISTER..........................................

    @PostMapping("/create/first/user")
    public ResponseEntity<RegisterResponseDto> createEmp(@RequestBody EmployeeDraftDto employeeDraftDto

                                       ){

//        Users loggedInUser=userPrinciple.getUser();

//        if (!hasRole(userPrinciple, "ENTERPRISE_ADMIN")) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .body("ACCESS DENIED ONLY FOR ENTERPRISE ADMIN ROLE");
//        }



        RegisterResponseDto response =
                employeeDraftService.createEmployeeAfterRegister(employeeDraftDto);

        return ResponseEntity.ok(response);

    }

       @GetMapping("/get/employee_draft")
        public  ResponseEntity<Boolean>ExistEmployeeByLogged(
                                                             @RequestParam String email){



        boolean emp=employeeDraftService.checkEmployeeByEmail(email);
        return ResponseEntity.ok(emp);
    }


//    @PostMapping("/employeedraft/to/employee")
//    public ResponseEntity<?>moveEmployeeDraft(
//                                              @AuthenticationPrincipal UserPrinciple userPrinciple){
//
//
//        Users loggedInUser=userPrinciple.getUser();
//
//        return employeeDraftService.moveEmployeeDraft(loggedInUser);
//    }

}
