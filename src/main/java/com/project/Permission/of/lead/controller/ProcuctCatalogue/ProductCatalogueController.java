package com.project.Permission.of.lead.controller.ProcuctCatalogue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.dto.ProductCatalogueDto;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.ProductCatalogueService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/enterprises")
@RestController


public class ProductCatalogueController {
    @Autowired
    private ProductCatalogueService productCatalogueService;

    @Autowired
    private ObjectMapper objectMapper;

    private boolean hasRole(UserPrinciple userPrinciple, String role) {
        return userPrinciple.getAuthorities().stream().
                anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }



    @PostMapping("/{eid}/bussinessunits/{buid}/product_group")
    public ResponseEntity<?> createProductGroup(
           @AuthenticationPrincipal UserPrinciple userPrinciple,
           @RequestBody(required = false) Map<String, Object> requestBody,
           @PathVariable String eid,
           @PathVariable String buid) {

        Users loggedInUser = userPrinciple.getUser();
        System.out.println("loggedInUser:" + userPrinciple.getUsername());
        if (!hasRole(userPrinciple, "BUSSINESS_ADMIN")&& !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ONLY HR MANAGER CAN CREATE PRODUCT");
         }

        ProductCatalogueDto productCatalogueDto = objectMapper.convertValue(requestBody, ProductCatalogueDto.class);

        ProductCatalogueDto createGroup = productCatalogueService.createProductGroup(productCatalogueDto, loggedInUser, eid,buid);
        return ResponseEntity.status(HttpStatus.CREATED).body(createGroup);

    }




    //..............createproduct type.............................................................

    @PostMapping("/{eid}/bussinessunits/{buid}/product_group/{pgid}/product_type")
    public ResponseEntity<?> createProductType(
                                           @PathVariable String eid,
                                            @PathVariable String buid,
                                            @PathVariable String pgid,

                                           @AuthenticationPrincipal UserPrinciple userPrinciple,
                                           @RequestBody Map<String, Object> requestBody) {
        Users loggedInUser=userPrinciple.getUser();

        if(!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ONlY BUSSINESS ADMIN CAN CREATE GROUP TYPE");
        }

            ProductCatalogueDto productCatalogueDto = objectMapper.convertValue(requestBody, ProductCatalogueDto.class);
            ProductCatalogueDto save=productCatalogueService.createProductType(productCatalogueDto,loggedInUser,eid,buid,pgid);
            return ResponseEntity.ok(save);
    }



    //.....create product sku........................................................
    @PostMapping("/{eid}/bussinessunits/{buid}/productgroup/{pgid}/product_type/{ptid}/product_sku")
    public ResponseEntity<?> createProductSku(
                                                    @PathVariable String eid,
                                                    @PathVariable String buid,
                                                    @PathVariable String pgid,
                                                    @PathVariable String ptid,
                                                    @AuthenticationPrincipal  UserPrinciple userPrinciple,
                                                   @RequestBody Map<String, Object> requestBody) {
        System.out.println("Enter into ksu creation:" );

        Users loggedInUser=userPrinciple.getUser();
        if(!hasRole(userPrinciple, "BUSSINESS_ADMIN") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ONLY BUSSINESS ADMIN CAN ACCESS");
        }


            ProductCatalogueDto productCatalogueDto=objectMapper.convertValue(requestBody, ProductCatalogueDto.class);
            ProductCatalogueDto saved=productCatalogueService.createProductSku(productCatalogueDto,loggedInUser,eid,buid,pgid,ptid);
            return ResponseEntity.ok(saved);

    }


//..........get product groups....................................................



    @GetMapping("/{eid}/bussinessunits/{buid}/product_group")
    public ResponseEntity<?> getProductGroups(@PathVariable String eid,
                                              @PathVariable String buid,
                                              @AuthenticationPrincipal UserPrinciple userPrinciple) {
        if(!hasRole(userPrinciple, "BUSSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ONlY BUSSINESS ADMIN CAN CREATE GROUP TYPE");
        }

            List<ProductCatalogueDto> productTypes = productCatalogueService.getProductGroups(eid,buid);
            return ResponseEntity.ok(productTypes);
        }



    /////....................get product types..................................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/product_group/{pgid}/product_type")
    public ResponseEntity<?> getProductTypes(@PathVariable String eid,
                                             @PathVariable String buid,
                                             @PathVariable String pgid,
                                             @AuthenticationPrincipal UserPrinciple userPrinciple) {

        if(!hasRole(userPrinciple, "BUSSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ON;Y BUSSINESS ADMIN CAN CREATE GROUP TYPE");
        }

        // return list of product types
            List<ProductCatalogueDto> productTypes = productCatalogueService.getProductTypes(eid,buid,pgid);
            return ResponseEntity.ok(productTypes);
        }


//........get product sku............................................................................

    @GetMapping("/{eid}/bussinessunits/{buid}/product_group/{pgid}/product_type/{ptid}/product_sku")
    public ResponseEntity<?> getProductSku(@PathVariable String eid,
                                           @PathVariable String buid,
                                           @PathVariable String pgid,
                                           @PathVariable String ptid,
                                           @AuthenticationPrincipal UserPrinciple userPrinciple) {


        if(!hasRole(userPrinciple, "BUSSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ON;Y BUSSINESS ADMIN CAN CREATE GROUP TYPE");
        }

            // return list of product types
            List<ProductCatalogueDto> productTypes = productCatalogueService.getProductSku(eid, buid, pgid,ptid);
            return ResponseEntity.ok(productTypes);
        }



   /// get product catalogue table.......................................................

   @GetMapping("/{eid}/bussinessunits/{buid}/product_catalogue")
   public ResponseEntity<?> getFullProductCatalogue(
           @PathVariable String eid,
           @PathVariable String buid,
           @AuthenticationPrincipal UserPrinciple userPrinciple) {

       if (!hasRole(userPrinciple, "BUSSINESS_ADMIN") &&
               !hasRole(userPrinciple, "HR MANAGER") && !hasRole(userPrinciple,"LEAD_ANALYST")) {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
       }

       List<ProductCatalogueDto> catalogue =
               productCatalogueService.getFullCatalogue(eid, buid);

       return ResponseEntity.ok(catalogue);
   }

}
