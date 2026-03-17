package com.project.Permission.of.lead.controller;


import com.project.Permission.of.lead.dto.CheckUpDto;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.EnterpriseService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class CheckController {




    @Autowired
    private EnterpriseService enterpriseService;

    private boolean hasRole(UserPrinciple userPrinciple, String role) {
        return userPrinciple.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    /// CHECK ENTERPRISE AND BUSSINESS EXISTS................................

    @RequestMapping("/check/status")
    public ResponseEntity<CheckUpDto> checkStatus(@AuthenticationPrincipal UserPrinciple userPrinciple){

        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN" ) && !hasRole(userPrinciple, "ENTERPRISE_ADMIN" ) && !hasRole(userPrinciple, "LEAD_ANALYST") && !hasRole(userPrinciple, "HR MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        Users loggedInUser=userPrinciple.getUser();

        return ResponseEntity.ok(enterpriseService.checkStatus(loggedInUser));
    }
}
