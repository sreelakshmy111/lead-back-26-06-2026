package com.project.Permission.of.lead.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.dto.LeadsDto;
import com.project.Permission.of.lead.dto.PersonalManagementDto;
import com.project.Permission.of.lead.dto.TeritoryDto;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.LeadsService;
import com.project.Permission.of.lead.service.PersonalManagementService;
import com.project.Permission.of.lead.service.TeritoryService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enterprises")
public class LeadsController {


    @Autowired
    private LeadsService leadsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeritoryService teritoryService;
    @Autowired
    private PersonalManagementService personalManagementService;

    private  boolean hasRole(UserPrinciple userPrinciple,String role){
        return userPrinciple.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_" + role));
    }


    /// CREATE LEADS...................................................................

    @PostMapping("{eid}/bussinessunits/{buid}/leads")

    public ResponseEntity<?> createLead(@RequestBody Map<String, Object> requestBody,
                                        @AuthenticationPrincipal  UserPrinciple userPrinciple,
                                        @PathVariable String eid,
                                        @PathVariable String buid) {

        Users loggedInUser=userPrinciple.getUser();
    if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
        return ResponseEntity.ok("access denied ,only bussiness admin can create leads..");
    }


        //[EA1000/bussinessunits/BU1000/lead]

        LeadsDto leadsDto=objectMapper.convertValue(requestBody,LeadsDto.class);
        LeadsDto createdlead=leadsService.createLead(leadsDto,loggedInUser,eid,buid);
        return ResponseEntity.ok(createdlead);

    }


    /// GET ALL LEADS.........................................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/leads")
    public ResponseEntity<?> getLeads(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                      @PathVariable String eid,
                                      @PathVariable String buid) {


        Users loggedInUser=userPrinciple.getUser();
        if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
            return ResponseEntity.ok("only bussiness admin can get leads..");
        }

        List<LeadsDto> leads=leadsService.getallLeads(eid,buid,loggedInUser,userPrinciple);
        return ResponseEntity.ok(leads);
        }


        /// GET LEADS BY ID..........................................................


        @GetMapping("/{eid}/bussinessunits/{buid}/leads/{lid}")
        public ResponseEntity<?> getLeads(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                          @PathVariable String eid,
                                          @PathVariable String buid,
                                          @PathVariable String lid) {


            Users loggedInUser=userPrinciple.getUser();
            if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
                return ResponseEntity.ok("only bussiness admin can get leads..");
            }

            LeadsDto leads=leadsService.getLeadsByLid(eid,buid,lid,loggedInUser,userPrinciple);
            return ResponseEntity.ok(leads);
        }




//    @GetMapping(value = "/enterprises", params = "eids")
//    public ResponseEntity<?> getLeads(
//            @RequestParam("eids") String baseParam,
//            @AuthenticationPrincipal UserPrinciple userPrinciple
//    )
//
//    {
//
//        if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
//            return ResponseEntity.ok("access denied ,only bussiness admin can create leads..");
//        }
//
//
//        String decodedEid = URLDecoder.decode(baseParam, StandardCharsets.UTF_8);
//
//        // decodeId = "EA1000/bussinessunits?buid=BU1000/leads"
//        decodedEid = decodedEid.trim();
//        boolean isLeads = decodedEid.endsWith("/leads");
//        if (isLeads) {
//            decodedEid = decodedEid.replace("/leads", "");
//            String[] parts = decodedEid.split("/bussinessunits\\?buid=");
//            String enterpriseId = parts[0];
//            String businessUnitId = parts[1];
//
//            // ✅ Print IDs to console
//            System.out.println("Enterprise ID in lead: " + enterpriseId);
//            System.out.println("Business Unit ID in lead: " + businessUnitId);
//
//            List<LeadsDto> leads = leadsService.getallLeads(
//                    enterpriseId, businessUnitId, userPrinciple.getUser(), userPrinciple
//            );
//            return ResponseEntity.ok(leads);
//        }
//
//        return ResponseEntity.badRequest().body("Action not recognized");
//    }





    ///..........ASSIGN EMPLOYEEEE..............................................

    @PutMapping("/{eid}/bussinessunits/{buid}/leads/{lid}/employee")
    public ResponseEntity<?> assignEmployee(@RequestBody Map<String, Object> requestBody,
                                            @AuthenticationPrincipal UserPrinciple userPrinciple,
                                            @PathVariable String eid,
                                            @PathVariable String buid,
                                            @PathVariable String lid) {

        Users loggedUser=userPrinciple.getUser();
        if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
            return ResponseEntity.ok("only bussiness admin can get leads..");
        }


            LeadsDto leadsDto=objectMapper.convertValue(requestBody,LeadsDto.class);
            LeadsDto leads=leadsService.assignEmployee(leadsDto,eid,buid,lid,loggedUser,userPrinciple);
            return ResponseEntity.ok(leads);
        }




    //// GET TERRITORIES UNDER EID AND BUID TO ASSIGH THE LEAD..............................................


    @GetMapping("/{eid}/bussinessunits/{buid}/territories")
    public ResponseEntity<?> getTerritorieForLead(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                            @PathVariable String eid,
                                            @PathVariable String buid) {

        Users loggedInUser=userPrinciple.getUser();
        if(!hasRole(userPrinciple,"BUSSNESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
            return ResponseEntity.ok("only bussiness admin can get leads..");
        }

        List<TeritoryDto> terittories=teritoryService.getTerritorriesByBuidAndEid(eid,buid,userPrinciple,loggedInUser);
        return ResponseEntity.ok(terittories);
    }


 /// ASSIGN TERRITORIES TO THE LEAD..................................................

    @PutMapping("/{eid}/bussinessunits/{buid}/leads/{lid}/territories/{tid}")
    public ResponseEntity<?> assignTerritoryToLead(@RequestBody Map<String,Object> requestBody,
                                                   @AuthenticationPrincipal UserPrinciple userPrinciple,
                                                   @PathVariable String eid,
                                                   @PathVariable String buid,
                                                   @PathVariable String lid,
                                                   @PathVariable String tid) {

        Users loggedInUser=userPrinciple.getUser();

        if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
            return ResponseEntity.ok("only bussiness admin can get leads..");
        }


        LeadsDto leadsDto=objectMapper.convertValue(requestBody,LeadsDto.class);
        LeadsDto leads=leadsService.assignTerritoryToLead(leadsDto,eid,buid,lid,tid,loggedInUser,userPrinciple);
        return ResponseEntity.ok(leads);
    }


    /// FETCH EMPLOYYESS UNDERR TERIITORIES..................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/territories/{tid}/employees")
    public ResponseEntity<?>getEmployeeUnderTerritory(@PathVariable String eid,
                                                      @PathVariable String buid,
                                                      @PathVariable String tid,
                                                      @AuthenticationPrincipal UserPrinciple userPrinciple){
        Users loggedInUser=userPrinciple.getUser();
        if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
            return ResponseEntity.ok("only bussiness admin can access the employyes under territoty");
        }

        List<PersonalManagementDto> employees=personalManagementService.getEmployeesUnderTerritory(eid,buid,tid,loggedInUser);

        return ResponseEntity.ok(employees);
    }


    /// ASSIGN EMPLOYEEE(TO TERRITORY) TO THE LEAD..........................................................................

    @PutMapping("/{eid}/bussinessunits/{buid}/leads/{lid}/territories/{tid}/employees/{empid}")
    public ResponseEntity<?>assignEmployeeToLead(@RequestBody Map<String,Object>requestBody,
                                                 @AuthenticationPrincipal UserPrinciple userPrinciple,
                                                 @PathVariable String eid,
                                                 @PathVariable String buid,
                                                 @PathVariable String lid,
                                                 @PathVariable String tid,
                                                 @PathVariable String empid
                                                 ){
        Users loggedInUser=userPrinciple.getUser();

        if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
            return ResponseEntity.ok("error only bussiness_admin can do this");
        }
        LeadsDto leadsDto=objectMapper.convertValue(requestBody,LeadsDto.class);
        LeadsDto leads=leadsService.assignEmployeeToLead(leadsDto,eid,buid,lid,tid,empid,loggedInUser,userPrinciple);
        return ResponseEntity.ok(leads);
    }



    /// LEADS AT A GLANCE LINK.................................................................
   @GetMapping("/bussinessunits/lead/glance")
    public ResponseEntity<?> leadGlance(@RequestParam String eid,
                                        @RequestParam String buid,
                                        @RequestParam(defaultValue = "0") int pageNo,
                                        @AuthenticationPrincipal UserPrinciple userPrinciple){

        if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("only bussienss admin can access the leads at glance");
        }

        Page<LeadsDto> glanceLead=leadsService.findLeadByGlance(eid,buid,pageNo);
        return ResponseEntity.ok(glanceLead);


    }



    //
//    public ResponseEntity<?> getTerritories(String decodedEid, UserPrinciple userPrinciple, Users loggedInUser) {
//
//      if(!hasRole(userPrinciple,"HR MANAGER")){
//          return ResponseEntity.ok("only hr manager can accesss the territories under eid and uid");
//      }
//
//      if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/territories")){
//          //[EA1000,bussinessunits?buid=BU1000/territories]
//
//          String[] eSplits=decodedEid.split("/bussinessunits\\?buid=");
//          String enterpriseId=eSplits[0].trim();
//          String[] bSplits=eSplits[1].split("/territories");
//          String bussinessUnitId=bSplits[0].trim();
//
//          List<LeadsDto> terittories=leadsService.getTerritorriesByBuidAndEid(enterpriseId,bussinessUnitId,userPrinciple,loggedInUser);
//          return ResponseEntity.ok(terittories);
//      }
//
//
//    }

    /// LEADS STAGE UPDATE.............................................................

    @PutMapping("{eid}/bussinessunits/{buid}/leads/{lid}/status")
    public ResponseEntity<?>updateLedasStage(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                             @PathVariable String eid,
                                             @PathVariable String buid,
                                             @PathVariable String lid,
                                             @RequestBody LeadsDto leadsDto){

        Users loggedInUser=userPrinciple.getUser();
        if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
            return ResponseEntity.ok("only bussiness admin can ipdate lead stages");
        }

        LeadsDto updatedStage=leadsService.updateLeadStage(eid,buid,lid,loggedInUser,leadsDto);
        return ResponseEntity.ok(updatedStage);
    }




// CREATE FOLOOW UP OF LEADS..................................................................

    @PutMapping("/{eid}/bussinessunits/{buid}/leads/{lid}/follow_up")
    public ResponseEntity<?>createFollowUp(@RequestBody Map<String,Object> requestData,
                                           @PathVariable String eid,
                                           @PathVariable String buid,
                                           @PathVariable String lid,
                                           @AuthenticationPrincipal UserPrinciple userPrinciple){
        Users loggedInUser=userPrinciple.getUser();

        if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
            return ResponseEntity.ok("only bussiness admin can create follow ups");
        }

        LeadsDto leadsDto=objectMapper.convertValue(requestData,LeadsDto.class);
        LeadsDto taskFollowUpDto =leadsService.createFollowUp(eid,buid,lid,leadsDto,loggedInUser);
        return ResponseEntity.ok(taskFollowUpDto);

    }


/// UPDATE LEAD.......................................................................

    @PutMapping("{eid}/bussinessunits/{buid}/leads/{lid}")
    public ResponseEntity<?>updateLead(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                       @PathVariable String eid,
                                       @PathVariable String buid,
                                       @PathVariable String lid,
                                       @RequestBody LeadsDto leadsDto){

        Users loggedInUser=userPrinciple.getUser();

        if(!hasRole(userPrinciple,"BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")){
            return ResponseEntity.ok("only bussiness admin can create follow ups");
        }
        LeadsDto leadsDto1=leadsService.updateLead(eid,buid,lid,leadsDto,loggedInUser);
        return ResponseEntity.ok(leadsDto1);
    }

}
