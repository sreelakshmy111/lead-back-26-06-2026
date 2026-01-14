package com.project.Permission.of.lead.controller.Enterprise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.controller.CustomerController;
import com.project.Permission.of.lead.controller.LeadsController;
import com.project.Permission.of.lead.controller.PersonalManagementController;
import com.project.Permission.of.lead.controller.ProcuctCatalogue.ProductCatalogueController;
import com.project.Permission.of.lead.controller.ProcuctCatalogue.ServiceCatalougeController;
import com.project.Permission.of.lead.dto.*;
import com.project.Permission.of.lead.entity.Catalogue.ServiceCatalogue;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.*;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

//@RestController
//@RequestMapping("/enterprises")
public class HierarchyCreateController {
    //

     @Autowired
     private EnterpriseService enterpriseService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private BussinessUnitService bussinessUnitService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ZoneService  zoneService;

    @Autowired
    private StateService stateService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private TeritoryService teritoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonalManagementController personalManagementController;

    @Autowired
    private ProductCatalogueController productCatalogueController;

    @Autowired
    private ServiceCatalougeController serviceCatalogueController;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private LeadsController leadsController;

    private boolean hasRole(UserPrinciple userPrinciple, String role) {
        return userPrinciple.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

//...................................//  CREATE //...................................................................................................................

    @PostMapping(params = "eid")

    public ResponseEntity<?> createResource(
            @RequestParam("eid") String eidParam,
            @RequestBody(required = false) Map<String, Object> requestBody,
            @AuthenticationPrincipal UserPrinciple userPrinciple,
            HttpServletRequest request) {



        try {
            String decodedEid = URLDecoder.decode(eidParam, StandardCharsets.UTF_8);
            System.out.println("decodedEid = " + decodedEid);
            Users loggedInUser = userPrinciple.getUser();


            // First, try reading from custom header
            String clientIp = request.getHeader("X-Client-IP");

            // If not present, fallback to actual request IP
            if (clientIp == null || clientIp.isEmpty()) {
                clientIp = request.getRemoteAddr();
            }

            System.out.println("Client IP Detected: " + clientIp);

            // You can now store clientIp in DB or log it
//            return ResponseEntity.ok("District updated from IP: " + clientIp);



//            ...............Teritory Create//.................................................

            if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
            && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=")
            && decodedEid.contains("/states?sid=") && decodedEid.contains("/districts?did=") && decodedEid.contains("/teritories")){
                if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){  // userprincipe represetnlogged in userrr...

                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED ONLY FOR BUSINESS ADMIN");
                }

                String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");   //  [1, 2/regions?rid=2/countries?cid=3/zones?zid=2/states?sid=1/districts?did=1/teritories]
                String enterpriseId=eSplits[0].trim();

                String [] bSplits=eSplits[1].split("/regions\\?rid=");   //  [ 2,2/countries?cid=3/zones?zid=2/states?sid=1/districts?did=1/teritories]
                String bussinessUnitId=bSplits[0].trim();

                String [] rSplits=bSplits[1].split("/countries\\?cid=");  //  [ 2,  3/zones?zid=2/states?sid=1/districts?did=1/teritories]
                String regionId=rSplits[0].trim();

                String [] cSplits=rSplits[1].split("/zones\\?zid=");              //  [   3,2/states?sid=1/districts?did=1/teritories]
                String countryId=cSplits[0].trim();

                String [] zSplits= cSplits[1].split("/states\\?sid=");             //  [  2,1/districts?did=1/teritories]
                String zoneId=zSplits[0].trim();

                String [] sSplits=zSplits[1].split("/districts\\?did=");     //  [  1,1/teritories]
                String stateId=sSplits[0].trim();

                String [] dSplits=sSplits[1].split("/teritories");  //  [  1]
                String districtId=dSplits[0].trim();


                TeritoryDto teritoryDto=objectMapper.convertValue(requestBody, TeritoryDto.class);
                TeritoryDto saveTeritory=teritoryService.createTeritory(teritoryDto,loggedInUser,districtId,stateId,zoneId,countryId,regionId,bussinessUnitId,enterpriseId);
                return ResponseEntity.ok().body(saveTeritory);
            }













//            .................//District Create...............................................

            else if(decodedEid.contains("/bussinessunits?buid=")&& decodedEid.contains("/regions?rid=")
             && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=")
                     && decodedEid.contains("/states?sid=") && decodedEid.contains("/districts")){
                 if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
                     return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACESS DENIED -ONLY BUSSINESS ADMIN CAN ACCESS");
                 }

                 //1/bussinessunits?buid=2/reions?rid=3/countries?cid=1/zones?zid=2/states?sid=4/district

                 String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");   //  [1, 2/reions?rid=3/countries?cid=2/zones?zid=2/states?sid=4/district]
                 String enterpriseId=eSplits[0].trim();

                 String [] bSplits=eSplits[1].split("/regions\\?rid=");  //  [2, 3/countries?cid=2/zones?zid=2/states?sid=4/district]
                 String bussinessUnitId=bSplits[0].trim();

                 String[] rSplits=bSplits[1].split("/countries\\?cid=");     //  [ 3, 2/zones?zid=2/states?sid=4/district]
                 String regionId=rSplits[0].trim();

                 String[] cSplits=rSplits[1].split("/zones\\?zid=");         //  [  2,2/states?sid=4/district]
                 String countryId=cSplits[0].trim();

                 String [] zSplits=cSplits[1].split("/states\\?sid=");                   //  [  2,4/district]
                 String zoneId=zSplits[0].trim();

                 String [] sSplits=zSplits[1].split("/districts");        //  [  4]
                 String stateId= sSplits[0].trim();

                 DistrictDto districtDto=objectMapper.convertValue(requestBody,DistrictDto.class);
                 DistrictDto saveDistrict=districtService.createDistrict(districtDto,loggedInUser,stateId,zoneId,countryId,regionId,bussinessUnitId,enterpriseId);
                 return ResponseEntity.status(HttpStatus.FORBIDDEN).body(saveDistrict);


             }



//            ....................//State Create............................................

             else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
              && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=") && decodedEid.contains("/states")){
                  if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
                      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED-Only forBUSSINESS ADMIN");

                  }

                  String [] eSplits=decodedEid.split("/bussinessunits\\?buid=") ;
                  System.out.println("eSplits: " + Arrays.toString(eSplits));
                  //    [1,  2/regions?rid=2/countries?cid=1?/zone?zid=5/state ]
                  String enterpriseId=eSplits[0].trim();

                  String [] bSplits=eSplits[1].split("/regions\\?rid=");    //    [ 2, 2/countries?cid=1?/zone?zid=5/state ]
                  System.out.println("bSplits: " + Arrays.toString(bSplits));
                  String bussinessUnitId=bSplits[0].trim();

                  String [] rSplits=bSplits[1].split("/countries\\?cid=");   //    [  2,  4/zone?zid=5/state ]
                  System.out.println("rSplits: " + Arrays.toString(rSplits));
                  String regionId=rSplits[0].trim();

                  String [] cSplits=rSplits[1].split("/zones\\?zid=");   //    [  4,  5/state ]
                  System.out.println("cSplits: " + Arrays.toString(cSplits));
                  String countryId=cSplits[0].trim();

                  String [] zSplits=cSplits[1].split("/states");   //    [  5/state ]
                  System.out.println("zSplits: " + Arrays.toString(zSplits));
                  String zoneId=zSplits[0].trim();

                  StateDto stateDto=objectMapper.convertValue(requestBody,StateDto.class);

                  StateDto savedState=stateService.createState(stateDto,loggedInUser,zoneId,countryId,regionId,bussinessUnitId,enterpriseId);

                   return ResponseEntity.status(HttpStatus.CREATED).body(savedState);

              }




//             .................//Zone create//------------------------------------------------


              else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
                      && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones")){
                  if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
                      return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED :Requires role BUSSINESS_ADMIN role");

                  }

                  String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");     //    [1,2/regions?rid=2/countries?cid=1?/zone]
                  String enterpriseId=eSplits[0].trim();

                  String [] bSplits=eSplits[1].split("/regions\\?rid=");   //   [2,2/countries?cid=1?/zone]
                  String bussinessunitId=bSplits[0].trim();

                  String [] rSplits=bSplits[1].split("/countries\\?cid=");   //   [2,1?/zone]
                  String regionId=rSplits[0].trim();

                  String[] cSplits=rSplits[1].split("/zones");   //   [1,/zone]
                  String countryId=cSplits[0].trim();

                  ZoneDto zoneDto = objectMapper.convertValue(requestBody, ZoneDto.class);

                  ZoneDto created =zoneService.createZone(zoneDto,enterpriseId,bussinessunitId,regionId,countryId,loggedInUser);
                  return ResponseEntity.status(HttpStatus.OK).body(created);




              }




//             .....................//  Country create..//....................................

           else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=") && decodedEid.contains("/countries")) {

                if (!hasRole(userPrinciple, "BUSINESS_ADMIN")) {

                    return new ResponseEntity<>(Map.of("error", "ACCESS DENIED :Requires role BUSSINESS_ADMIN role"), HttpStatus.FORBIDDEN);
                }

                String[] eSpits = decodedEid.split("/bussinessunits\\?buid=");
                String enterpriseId = eSpits[0].trim();

                String[] buSplits = eSpits[1].split("/regions\\?rid=");
                String bussinesUnitId = buSplits[0].trim();

                String [] rSplits=buSplits[1].split("/countries");
                String regionId=rSplits[0].trim();

                CountryDto countryDto = objectMapper.convertValue(requestBody, CountryDto.class);

                CountryDto created = countryService.createCountry(countryDto, enterpriseId, bussinesUnitId, regionId, loggedInUser);
                return ResponseEntity.ok(created);

            }

//         ..................   Region creations..............................................


          else  if (decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions")) {
                if (!hasRole(userPrinciple, "BUSINESS_ADMIN"))
                    return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSINESS_ADMIN for Create."), HttpStatus.FORBIDDEN);

                // 1. Parsing Region IDs
                String[] enterpriseSplit = decodedEid.split("/bussinessunits\\?buid=");
                String enterpriseId = enterpriseSplit[0].trim();
                String[] buSplit = enterpriseSplit[1].split("/regions");
                String bussinessUnitId = buSplit[0].trim();

                // 2. Security and Routing based on Method
                // CREATE 🟢

                RegionDto regionDto = objectMapper.convertValue(requestBody, RegionDto.class);
                RegionDto createdRegion = regionService.createRegions(regionDto, enterpriseId, bussinessUnitId, loggedInUser);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdRegion);


            }
//
//          //------------Define Poduct SKu------------------------\\
//            else if (decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/productgroup?pgid=") && decodedEid.contains("/product_type?ptid=") && decodedEid.contains("/product_sku")){
//                return productCatalogueController.createProductSku(decodedEid,loggedInUser,userPrinciple,requestBody);
//            }
//
//
//            // --- Define product Type ---
//            else if (decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/productgroup?pgid=") && decodedEid.contains("/product_type")){
//                return productCatalogueController.createProductType(decodedEid,loggedInUser,userPrinciple,requestBody);
//            }
//
//        // --- Define product group.... ---
//            else if (decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/product") && decodedEid.contains("/productgroup")){
//              return productCatalogueController.createProductGroup(decodedEid,loggedInUser,userPrinciple,requestBody);
//            }

            // --- Check for Employee first ---
//            else if ( decodedEid.contains("/employee")) {
//                return personalManagementController.createEmployee(decodedEid, requestBody, loggedInUser, userPrinciple);
//            }


//            //,............Define Service item...........................................
//            else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/service_group?sgid=") && decodedEid.contains("/service_type?stid=") && decodedEid.contains("/service_item")){
//                return serviceCatalogueController.createServiceItem(requestBody,loggedInUser,userPrinciple,decodedEid);
//            }
//
//            //,............Define Service Type...........................................
//            else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/service_group?sgid=") && decodedEid.contains("/service_type")){
//                return serviceCatalogueController.createServiceType(requestBody,loggedInUser,userPrinciple,decodedEid);
//            }
//
//            //,............Define Service Group...........................................
//
//            else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/service_group")){
//                return serviceCatalogueController.createServiceGroup(requestBody,loggedInUser,userPrinciple,decodedEid);
//            }


//            else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/customer")){
//                return customerController.createCustomer(requestBody,decodedEid,loggedInUser,userPrinciple);
//            }


            //...............lead creation.......................................

//            if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/leads")){
//                return leadsController.createLead(requestBody,userPrinciple,loggedInUser,decodedEid);
//            }

            //         ..................   bussinessunit creations..............................................

               else if (decodedEid.contains("/bussinessunits")) {

                // 1. Parsing BU ID
                String[] parts = decodedEid.split("/bussinessunits");
                if (parts.length > 1 && !parts[1].trim().isEmpty())
                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid path segment after /bussinessunits."));
                String enterpriseId = parts[0].trim();

                // 2. Security and Routing based on Method

                if (!hasRole(userPrinciple, "ENTERPRISE_ADMIN"))
                    return new ResponseEntity<>(Map.of("error", "Access Denied: Requires ENTERPRISE_ADMIN for Create."), HttpStatus.FORBIDDEN);

                BussinessUnitDto bussinessUnitDto =objectMapper.convertValue(requestBody, BussinessUnitDto.class);
                BussinessUnitDto createdBu = bussinessUnitService.createBussinessUnit(enterpriseId, bussinessUnitDto, loggedInUser);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdBu);

            }

            else {
                // Extract enterpriseId safely from decodedEid
                // decodedEid may be "1" or "1/" or "1/enterprises"

                String[] parts = decodedEid.split("/");
                Long enterpriseId = Long.parseLong(parts[0].trim());

                EnterpriseDto enterpriseDto = objectMapper.convertValue(requestBody, EnterpriseDto.class);


                EnterpriseDto saved=enterpriseService.createEnterprise(enterpriseDto,loggedInUser);

                return ResponseEntity.ok(saved);
            }


            // 🚨 If nothing matched
//            return ResponseEntity.badRequest().body(Map.of("error", "Invalid EID format."));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }





//
////    ....................///..//.GET MAPPING...////..............................................................
//
//    @GetMapping(params = "eid")
//    public ResponseEntity<?> getHierarchy(
//            @RequestParam("eid") String eidParams,
////            @RequestBody(required = false) Map<String, Object> requiredBody,
//            @AuthenticationPrincipal UserPrinciple userPrinciple)
//
//    {
//
//        String decodedEid= URLDecoder.decode(eidParams, StandardCharsets.UTF_8);
//        Users loggedInUser = userPrinciple.getUser();
//
//        try{
//            if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions")){
//
//                if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
//                    return new ResponseEntity<>(Map.of("error","ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
//                }
//
//                String eSplits[]=decodedEid.split("/bussinessunits\\?buid=");
//                Long enterpriseId=Long.parseLong(eSplits[0].trim());
//
//                String bSplits[]=eSplits[1].split("/regions");
//                Long bussinessUnitId=Long.parseLong(bSplits[0].trim());
//
////                    RegionDto regionDto=objectMapper.convertValue(requiredBody, RegionDto.class);
//                List<RegionDto> regions= regionService.getRegions(enterpriseId,bussinessUnitId);
//                return ResponseEntity.ok(regions);
//
//            } else if (decodedEid.contains("/bussinessunits")) {
//                if(!hasRole(userPrinciple,"ENTERPRISE_ADMIN")){
//                    return new ResponseEntity<>(Map.of("error" ,"ACCESS DENIED :only ENTERPRISE ADMIN CAN GET"),HttpStatus.FORBIDDEN);
//                }
//
//                String bSlits[]=decodedEid.split("/bussinessunits");
//                Long enterpriseId=Long.parseLong(bSlits[0].trim());
//
//                List<BussinessUnitDto> busniess=bussinessUnitService.getBussinessEnterpriseById(enterpriseId);
//                return ResponseEntity.ok(busniess);
//
//            }
//
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error invalid urls");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("error", "Error: " + e.getMessage()));
//
//        }
//
//
//    }


}





