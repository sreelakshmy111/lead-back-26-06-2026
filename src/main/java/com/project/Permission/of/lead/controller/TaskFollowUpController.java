package com.project.Permission.of.lead.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.dto.LeadsDto;
import com.project.Permission.of.lead.dto.TaskFollowUpDto;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.TaskFollowUpService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@RestController

public class TaskFollowUpController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskFollowUpService taskFollowUpService;

    private boolean hasRole(UserPrinciple userPrinciple, String role) {
        return userPrinciple.getAuthorities().stream().
                anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }


    /// GET follow ups.................................................

    @GetMapping("/bussinessunits/{buid}/leads/{lid}/follow_up")
    public ResponseEntity<?>getFollowUps(@AuthenticationPrincipal UserPrinciple userPrinciple,

                                         @PathVariable String buid,
                                         @PathVariable String lid){
        Users loggedInUser=userPrinciple.getUser();

        if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
            return ResponseEntity.ok("only bussiness admin can get foloow ups");
        }

        List<TaskFollowUpDto> followUps=taskFollowUpService.getFollowUps(buid,lid,loggedInUser);
        return ResponseEntity.ok(followUps);
    }

}