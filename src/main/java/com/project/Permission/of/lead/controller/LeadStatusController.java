package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.LeadStatusCustomDto;
import com.project.Permission.of.lead.entity.LeadStatusCustom;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.repository.LeadStatusCustomRepository;
import com.project.Permission.of.lead.service.BussinessUnitService;
import com.project.Permission.of.lead.service.LeadStatusService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enterprises")
public class LeadStatusController {

    @Autowired
    private LeadStatusService leadStatusService;
    @Autowired
    private LeadStatusCustomRepository leadStatusCustomRepository;

    @Autowired
    private BussinessUnitService bussinessUnitService;


    private boolean hasRole(UserPrinciple userPrinciple, String role) {
        return userPrinciple.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }


    @PostMapping("{eid}/bussinessunits/{buid}/lead/status")
    public ResponseEntity<?> leadStatus(
            @RequestBody LeadStatusCustomDto leadStatusCustomDto,
            @AuthenticationPrincipal UserPrinciple userPrinciple,
            @PathVariable String eid,
            @PathVariable String buid) {


        Users loggedInUser = userPrinciple.getUser();
        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST") &&!hasRole(userPrinciple,"ENTERPRISE_ADMIN")) {
            return ResponseEntity.ok("only bussiness admin can change lead status");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
        LeadStatusCustomDto status = leadStatusService.leadStatus(eid, buid, leadStatusCustomDto, loggedInUser);
        return ResponseEntity.ok(status);


    }

    /// GET LEAD_STATUS_CUSTOM............................................

    @GetMapping("/{eid}/bussinessunits/{buid}/lead/status")
    public ResponseEntity<?> getleadStatus(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                           @PathVariable String eid,
                                           @PathVariable String buid) {

        Users loggedInUser = userPrinciple.getUser();

        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST") &&!hasRole(userPrinciple,"ENTERPRISE_ADMIN")) {
            return ResponseEntity.ok("only bussiness admin can get lead_status");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
        List<LeadStatusCustomDto> stages = leadStatusService.getLeadStages(eid, buid, loggedInUser);
        return ResponseEntity.ok(stages);
    }

    ///  ADD LEAD STAGES....................................................

    @PostMapping("/{eid}/bussinessunits/{buid}/lead_stage_add")
    public ResponseEntity<?> addLeadStage(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                          @PathVariable String eid,
                                          @PathVariable String buid,
                                          @RequestBody LeadStatusCustomDto leadStatusCustomDto) {

        Users loggedInUser = userPrinciple.getUser();
        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST") &&!hasRole(userPrinciple,"ENTERPRISE_ADMIN")) {
            return ResponseEntity.ok("only bussiness admin can create new stages");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
        LeadStatusCustomDto addStage = leadStatusService.createNewStage(eid, buid, leadStatusCustomDto, loggedInUser);


        return ResponseEntity.ok(addStage);
    }


    /// DELETE LEAD STAGE...................................................

    @DeleteMapping("{eid}/bussinessunits/{buid}/lead_stages/{stageId}")
    public ResponseEntity<?> deleteLeadStage(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                             @PathVariable String eid,
                                             @PathVariable String buid,
                                             @PathVariable Long stageId) {

        Users loggedInUser = userPrinciple.getUser();

        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST") &&!hasRole(userPrinciple,"ENTERPRISE_ADMIN")) {
            return ResponseEntity.ok("only bussiness admin can delete the stage");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
        LeadStatusCustomDto reponse = leadStatusService.deleteStage(eid, buid, stageId);
        return ResponseEntity.ok("DELETEION SUCCESSFULL OF STAGE");
    }


    /// EDIT LEAD STAGES..................................................

    @PutMapping("{eid}/bussinessunits/{buid}/lead_stage_edit/{stageId}")
    public ResponseEntity<?> editLeadStage(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                           @RequestBody LeadStatusCustomDto leadStatusCustomDto,
                                           @PathVariable String eid,
                                           @PathVariable String buid,
                                           @PathVariable Long stageId) {
        Users loggedInUser = userPrinciple.getUser();

        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST") &&!hasRole(userPrinciple,"ENTERPRISE_ADMIN")) {
            return ResponseEntity.ok("only bussiness admin can edit lead stages");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
        LeadStatusCustomDto stages = leadStatusService.editLeadsStage(eid, buid, stageId, leadStatusCustomDto);
        return ResponseEntity.ok(stages);
    }


    @PutMapping("/{eid}/bussinessunits/{buid}/lead_stage_move/{stageId}")
    public ResponseEntity<?> moveStage(
            @PathVariable String eid,
            @PathVariable String buid,
            @PathVariable Long stageId,
            @RequestBody Map<String, String> body
    ) {
        String direction = body.get("direction");
        leadStatusService.moveStage(eid, buid, stageId, direction);
        return ResponseEntity.ok("Stage moved");
    }


    ///  GET LEADS STAGE FIRST IN ASCENDING ORDER..................................

    @GetMapping("{eid}/bussinessunits/{buid}/lead_stage_asc")
    public ResponseEntity<?>getLeadByAsc(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                         @PathVariable String eid,
                                         @PathVariable String buid){
        Users loggedInUser=userPrinciple.getUser();

        if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST") &&!hasRole(userPrinciple,"ENTERPRISE_ADMIN")){
            return ResponseEntity.ok("bussiness admin can get the lead stsge");
        }

        bussinessUnitService.validBuAccess(userPrinciple, buid);
        LeadStatusCustomDto lead=leadStatusService.findLeadByAsc(eid,buid,loggedInUser);
        return ResponseEntity.ok(lead);
    }


    ///  Save lead stage in lms_table

//    @PutMapping("{eid}/bussinessunits/{buid}/lead/{lid}/stage")
//    public ResponseEntity<?>getLeadByAsc(@AuthenticationPrincipal UserPrinciple userPrinciple,
//                                         @PathVariable String eid,
//                                         @PathVariable String buid,
//                                         @PathVariable String lid){
//        Users loggedInUser=userPrinciple.getUser();
//
//        if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
//            return ResponseEntity.ok("bussiness admin can get the lead stsge");
//        }
//
//        LeadStatusCustomDto lead=leadStatusService.saveLeadStage(eid,buid,lid,loggedInUser);
//        return ResponseEntity.ok(lead);
//    }


}