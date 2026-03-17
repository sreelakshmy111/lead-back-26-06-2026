package com.project.Permission.of.lead.controller.Enterprise;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.controller.CustomerController;
import com.project.Permission.of.lead.controller.LeadsController;
import com.project.Permission.of.lead.controller.PersonalManagementController;
import com.project.Permission.of.lead.controller.ProcuctCatalogue.ProductCatalogueController;
import com.project.Permission.of.lead.controller.ProcuctCatalogue.ServiceCatalougeController;
import com.project.Permission.of.lead.controller.TeritoryController;
import com.project.Permission.of.lead.dto.*;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.service.*;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import static com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator.Validity.DENIED;

//@RestController
//@RequestMapping("/enterprises")
public class GetHierarchyController {
//
//    @Autowired
//    private EnterpriseService enterpriseService;
//
//    @Autowired
//    private BussinessUnitService  bussinessUnitService;
//
//    @Autowired
//    private RegionService regionService;
//
//    @Autowired
//    private CountryService countryService;
//
//    @Autowired
//    private ZoneService zoneService;
//
//    @Autowired
//    private StateService stateService;
//
//    @Autowired
//    private DistrictService districtService;
//
//    @Autowired
//    private TeritoryService teritoryService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private PersonalManagementController personalManagementController;
//
//    @Autowired
//    private ProductCatalogueController productCatalogueController;
//
//    @Autowired
//    private ServiceCatalougeController serviceCatalougeController;
//
//    @Autowired
//    private CustomerController customerController;
//
//    @Autowired
//    private LeadsController leadsController;
//
//    @Autowired
//    private TeritoryController teritoryController;

//    private boolean hasRole(UserPrinciple userPrinciple, String role) {
//        return userPrinciple.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
//    }

//    @GetMapping(value = "", params = "eid")
//    public ResponseEntity<?> getHierarchy(
//            @RequestParam("eid") String eidParams,
////            @RequestBody(required = false) Map<String, Object> requiredBody,
//            @AuthenticationPrincipal UserPrinciple userPrinciple)
//
//       {
//
//            String decodedEid= URLDecoder.decode(eidParams, StandardCharsets.UTF_8);
//            Users loggedInUser = userPrinciple.getUser();


// .............................// READ.......................................................................



//            try{
//
//                //      ...........// Teritories read BY Id........................................
//
//                if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
//                        && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=")
//                        && decodedEid.contains("/states?sid=") && decodedEid.contains("/districts?did=") && decodedEid.contains("/teritories?tid=")){
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER")){
//                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED ONLY FOR BUSINESS ADMIN");
//                    }
//
//                    String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");   //  [1, 2/regions?rid=2/countries?cid=3/zones?zid=2/states?sid=1/districts?did=1/teritories]
//                    String enterpriseId=eSplits[0].trim();
//
//                    String [] bSplits=eSplits[1].split("/regions\\?rid=");   //  [ 2,2/countries?cid=3/zones?zid=2/states?sid=1/districts?did=1/teritories]
//                    String bussinessUnitId=bSplits[0].trim();
//
//                    String [] rSplits=bSplits[1].split("/countries\\?cid=");  //  [ 2,  3/zones?zid=2/states?sid=1/districts?did=1/teritories]
//                    String regionId=rSplits[0].trim();
//
//                    String [] cSplits=rSplits[1].split("/zones\\?zid=");              //  [   3,2/states?sid=1/districts?did=1/teritories]
//                    String countryId=cSplits[0].trim();
//
//                    String [] zSplits= cSplits[1].split("/states\\?sid=");             //  [  2,1/districts?did=1/teritories]
//                    String zoneId=zSplits[0].trim();
//
//                    String [] sSplits=zSplits[1].split("/districts\\?did=");     //  [  1,1/teritories]
//                    String stateId=sSplits[0].trim();
//
//                    String [] dSplits=sSplits[1].split("/teritories\\?tid=");  //  [  1]
//                    String districtId=dSplits[0].trim();
//                    String territoryId=dSplits[1].trim();
//
//
//                    TeritoryDto teritoryDtos=teritoryService.getTeritorieById(territoryId,districtId,stateId,zoneId,countryId,regionId,bussinessUnitId,enterpriseId);
//                    return ResponseEntity.ok(teritoryDtos);
//                }


                //      ...........// Teritories read.........................................
//
//                if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
//                        && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=")
//                        && decodedEid.contains("/states?sid=") && decodedEid.contains("/districts?did=") && decodedEid.contains("/teritories")){
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER")){
//                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED ONLY FOR BUSINESS ADMIN");
//                    }
//
//                    String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");   //  [1, 2/regions?rid=2/countries?cid=3/zones?zid=2/states?sid=1/districts?did=1/teritories]
//                    String enterpriseId=eSplits[0].trim();
//
//                    String [] bSplits=eSplits[1].split("/regions\\?rid=");   //  [ 2,2/countries?cid=3/zones?zid=2/states?sid=1/districts?did=1/teritories]
//                    String bussinessUnitId=bSplits[0].trim();
//
//                    String [] rSplits=bSplits[1].split("/countries\\?cid=");  //  [ 2,  3/zones?zid=2/states?sid=1/districts?did=1/teritories]
//                    String regionId=rSplits[0].trim();
//
//                    String [] cSplits=rSplits[1].split("/zones\\?zid=");              //  [   3,2/states?sid=1/districts?did=1/teritories]
//                    String countryId=cSplits[0].trim();
//
//                    String [] zSplits= cSplits[1].split("/states\\?sid=");             //  [  2,1/districts?did=1/teritories]
//                    String zoneId=zSplits[0].trim();
//
//                    String [] sSplits=zSplits[1].split("/districts\\?did=");     //  [  1,1/teritories]
//                    String stateId=sSplits[0].trim();
//
//                    String [] dSplits=sSplits[1].split("/teritories");  //  [  1]
//                    String districtId=dSplits[0].trim();
//
//
//                   List<TeritoryDto> teritoryDtos=teritoryService.getTeritories(districtId,stateId,zoneId,countryId,regionId,bussinessUnitId,enterpriseId);
//                   return ResponseEntity.ok(teritoryDtos);
//                }
//

            // .................//District Read.ById..............................................

//
//
//                else if(decodedEid.contains("/bussinessunits?buid=")&& decodedEid.contains("/regions?rid=")
//                        && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=")
//                        && decodedEid.contains("/states?sid=") && decodedEid.contains("/districts?did=")){
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER")){
//                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACESS DENIED -ONLY BUSSINESS ADMIN CAN ACCESS");
//                    }
//
//                    //1/bussinessunits?buid=2/reions?rid=3/countries?cid=1/zones?zid=2/states?sid=4/district
//
//                    String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");   //  [1, 2/reions?rid=3/countries?cid=2/zones?zid=2/states?sid=4/district]
//                    String enterpriseId=eSplits[0].trim();
//
//                    String [] bSplits=eSplits[1].split("/regions\\?rid=");  //  [2, 3/countries?cid=2/zones?zid=2/states?sid=4/district]
//                    String bussinessUnitId=bSplits[0].trim();
//
//                    String[] rSplits=bSplits[1].split("/countries\\?cid=");     //  [ 3, 2/zones?zid=2/states?sid=4/district]
//                    String regionId=rSplits[0].trim();
//
//                    String[] cSplits=rSplits[1].split("/zones\\?zid=");         //  [  2,2/states?sid=4/district]
//                    String countryId=cSplits[0].trim();
//
//                    String [] zSplits=cSplits[1].split("/states\\?sid=");                   //  [  2,4/district]
//                    String zoneId=zSplits[0].trim();
//
//                    String [] sSplits=zSplits[1].split("/districts\\?did=");        //  [  4]
//                    String stateId=sSplits[0].trim();
//                    String districtId=sSplits[1].trim();
//
//
//                    DistrictDto districtDtos=districtService.getDistrictById(districtId,stateId,zoneId,countryId,regionId,bussinessUnitId,enterpriseId);
//                    return ResponseEntity.ok().body(districtDtos);
//
//                }



////            .................//District Read...............................................
//
//
//
//                else if(decodedEid.contains("/bussinessunits?buid=")&& decodedEid.contains("/regions?rid=")
//                        && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=")
//                        && decodedEid.contains("/states?sid=") && decodedEid.contains("/districts")){
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER")){
//                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACESS DENIED -ONLY BUSSINESS ADMIN CAN ACCESS");
//                    }
//
//                    //1/bussinessunits?buid=2/reions?rid=3/countries?cid=1/zones?zid=2/states?sid=4/district
//
//                    String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");   //  [1, 2/reions?rid=3/countries?cid=2/zones?zid=2/states?sid=4/district]
//                    String enterpriseId=eSplits[0].trim();
//
//                    String [] bSplits=eSplits[1].split("/regions\\?rid=");  //  [2, 3/countries?cid=2/zones?zid=2/states?sid=4/district]
//                    String bussinessUnitId=bSplits[0].trim();
//
//                    String[] rSplits=bSplits[1].split("/countries\\?cid=");     //  [ 3, 2/zones?zid=2/states?sid=4/district]
//                    String regionId=rSplits[0].trim();
//
//                    String[] cSplits=rSplits[1].split("/zones\\?zid=");         //  [  2,2/states?sid=4/district]
//                    String countryId=cSplits[0].trim();
//
//                    String [] zSplits=cSplits[1].split("/states\\?sid=");                   //  [  2,4/district]
//                    String zoneId=zSplits[0].trim();
//
//                    String [] sSplits=zSplits[1].split("/districts");        //  [  4]
//                    String stateId=sSplits[0].trim();
//
//
//                    List<DistrictDto> districtDtos=districtService.getDistricts(stateId,zoneId,countryId,regionId,bussinessUnitId,enterpriseId);
//                    return ResponseEntity.ok().body(districtDtos);
//
//                }


                //      ...........// States read Byid........................................
//
//                else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
//                        && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=") && decodedEid.contains("/states?sid=")){
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
//                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED-Only forBUSSINESS ADMIN");
//
//                    }
//
//                    String [] eSplits=decodedEid.split("/bussinessunits\\?buid=") ;
//                    System.out.println("eSplits: " + Arrays.toString(eSplits));
//                    //    [1,  2/regions?rid=2/countries?cid=1?/zone?zid=5/state ]
//                    String enterpriseId=eSplits[0].trim();
//
//                    String [] bSplits=eSplits[1].split("/regions\\?rid=");    //    [ 2, 2/countries?cid=1?/zone?zid=5/state ]
//                    System.out.println("bSplits: " + Arrays.toString(bSplits));
//                    String bussinessUnitId =bSplits[0].trim();
//
//                    String [] rSplits=bSplits[1].split("/countries\\?cid=");   //    [  2,  4/zone?zid=5/state ]
//                    System.out.println("rSplits: " + Arrays.toString(rSplits));
//                    String regionId= rSplits[0].trim();
//
//                    String [] cSplits=rSplits[1].split("/zones\\?zid=");   //    [  4,  5/state ]
//                    System.out.println("cSplits: " + Arrays.toString(cSplits));
//                    String countryId=cSplits[0].trim();
//
//                    String [] zSplits=cSplits[1].split("/states\\?sid=");   //    [  5/state ]
//                    System.out.println("zSplits: " + Arrays.toString(zSplits));
//                    String zoneId=zSplits[0].trim();
//                    String stateId=zSplits[1].trim();
//
//                    StateDto stateDtos=stateService.getStateById(zoneId,countryId,regionId,bussinessUnitId,enterpriseId,stateId);
//                    return ResponseEntity.status(HttpStatus.OK).body(stateDtos);
//
//
//                }




                //      ...........// States read.........................................
//
//               else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
//                        && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=") && decodedEid.contains("/states")){
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER")){
//                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED-Only forBUSSINESS ADMIN");
//
//                    }
//
//                    String [] eSplits=decodedEid.split("/bussinessunits\\?buid=") ;
//                    System.out.println("eSplits: " + Arrays.toString(eSplits));
//                    //    [1,  2/regions?rid=2/countries?cid=1?/zone?zid=5/state ]
//                    String enterpriseId=eSplits[0].trim();
//
//                    String [] bSplits=eSplits[1].split("/regions\\?rid=");    //    [ 2, 2/countries?cid=1?/zone?zid=5/state ]
//                    System.out.println("bSplits: " + Arrays.toString(bSplits));
//                    String bussinessUnitId =bSplits[0].trim();
//
//                    String [] rSplits=bSplits[1].split("/countries\\?cid=");   //    [  2,  4/zone?zid=5/state ]
//                    System.out.println("rSplits: " + Arrays.toString(rSplits));
//                    String regionId= rSplits[0].trim();
//
//                    String [] cSplits=rSplits[1].split("/zones\\?zid=");   //    [  4,  5/state ]
//                    System.out.println("cSplits: " + Arrays.toString(cSplits));
//                    String countryId=cSplits[0].trim();
//
//                    String [] zSplits=cSplits[1].split("/states");   //    [  5/state ]
//                    System.out.println("zSplits: " + Arrays.toString(zSplits));
//                    String zoneId=zSplits[0].trim();
//
//                    List<StateDto> stateDtos=stateService.getStates(zoneId,countryId,regionId,bussinessUnitId,enterpriseId);
//                    return ResponseEntity.status(HttpStatus.OK).body(stateDtos);
//
//
//                }


                //      ...........// Zoness read By id........................................

//                else  if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
//                        && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=")){
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
//                        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED :Requires role BUSSINESS_ADMIN role");
//
//                    }
//
//                    String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");     //    [1,2/regions?rid=2/countries?cid=1?/zone]
//                    String enterpriseId=eSplits[0].trim();
//
//                    String [] bSplits=eSplits[1].split("/regions\\?rid=");   //   [2,2/countries?cid=1?/zone]
//                    String bussinessunitId=bSplits[0].trim();
//
//                    String [] rSplits=bSplits[1].split("/countries\\?cid=");   //   [2,1/zone]
//                    String regionId=rSplits[0].trim();
//
//                    String[] cSplits=rSplits[1].split("/zones\\?zid=");   //   [1,/zone]->  [1]
//                    String countryId=cSplits[0].trim();
//                    String zoneId=cSplits[1].trim();
//
//                    ZoneDto zoneDto=zoneService.getZonesById(countryId,regionId,bussinessunitId,enterpriseId,zoneId);
//                    return ResponseEntity.status(HttpStatus.OK).body(zoneDto);
//
//                }




                //      ...........// Zoness read.........................................
//
//              else  if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
//                        && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones")){
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER")){
//                        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED :Requires role BUSSINESS_ADMIN role");
//
//                    }
//
//                    String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");     //    [1,2/regions?rid=2/countries?cid=1?/zone]
//                    String enterpriseId=eSplits[0].trim();
//
//                    String [] bSplits=eSplits[1].split("/regions\\?rid=");   //   [2,2/countries?cid=1?/zone]
//                    String bussinessunitId=bSplits[0].trim();
//
//                    String [] rSplits=bSplits[1].split("/countries\\?cid=");   //   [2,1/zone]
//                    String regionId=rSplits[0].trim();
//
//                    String[] cSplits=rSplits[1].split("/zones");   //   [1,/zone]->  [1]
//                    String countryId=cSplits[0].trim();
//
//                    List<ZoneDto> zoneDto=zoneService.getZones(countryId,regionId,bussinessunitId,enterpriseId);
//                    return ResponseEntity.status(HttpStatus.OK).body(zoneDto);
//
//                }

                //              ...................// Countries read Byid...........................................

//                else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=") && decodedEid.contains("/countries?cid=")){
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
//                        return new ResponseEntity<>(Map.of("error","ACSESS DENIED ONLY BUSSINESS_ADMIN CAN ACCESS THI METHOD"),HttpStatus.FORBIDDEN);
//                    }
//
//                    String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");   // eplits=[4,3/regions?rid=2/countries]
//                    String enterpriseId=eSplits[0].trim();
//
//                    String [] buSplits=eSplits[1].split("/regions\\?rid=");       // buplits=[3,2/countries]
//                    String bussinessUnitId=buSplits[0].trim();
//
//                    String [] rSplits=buSplits[1].split("/countries\\?cid=");
//                    String regionId=rSplits[0].trim();
//                    String countryId=rSplits[1].trim();
//
//                    CountryDto countryDtos=countryService.getCoutriesById(enterpriseId,bussinessUnitId,regionId,countryId);
//                    return ResponseEntity.ok(countryDtos);
//
//                }



           //              ...................// Countries read.........................................

//               else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=") && decodedEid.contains("/countries")){
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER")){
//                        return new ResponseEntity<>(Map.of("error","ACSESS DENIED ONLY BUSSINESS_ADMIN CAN ACCESS THI METHOD"),HttpStatus.FORBIDDEN);
//                    }
//
//                    String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");   // eplits=[4,3/regions?rid=2/countries]
//                    String enterpriseId=eSplits[0].trim();
//
//                    String [] buSplits=eSplits[1].split("/regions\\?rid=");       // buplits=[3,2/countries]
//                    String bussinessUnitId=buSplits[0].trim();
//
//                    String [] rSplits=buSplits[1].split("/countries");
//                    String regionId=rSplits[0].trim();
//
//                    List<CountryDto> countryDtos=countryService.getCoutries(enterpriseId,bussinessUnitId,regionId);
//                    return ResponseEntity.ok(countryDtos);
//
//                }

                /// ...................get employees under territories..........................................

//                else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/territory?tid=") && decodedEid.contains("/employees")){
//                    return personalManagementController.getEmployees(decodedEid,userPrinciple,loggedInUser);
//                }

//
//                //              ...................// regions read Byid.......................................
//
//                if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")){
//
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
//                        return new ResponseEntity<>(Map.of("error","ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
//                    }
//                    // 1/bussinessunits?buid=2/region?rid=4
//
//                    String eSplits[]=decodedEid.split("/bussinessunits\\?buid=");   // 1,2/region?rid=4
//                    String enterpriseId=eSplits[0].trim();
//
//                    String bSplits[]=eSplits[1].split("/regions\\?rid=");   //  2,4
//                    String bussinessUnitId=bSplits[0].trim();
//                    String regionId=bSplits[1].trim();
//
////                    RegionDto regionDto=objectMapper.convertValue(requiredBody, RegionDto.class);
//                    RegionDto regions= regionService.getRegionById(enterpriseId,bussinessUnitId,regionId);
//                    return ResponseEntity.ok(regions);
//
//                }




                //              ...................// regions read.........................................

//                if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions")){
//
//                    if(!hasRole(userPrinciple,"BUSINESS_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER")){
//                        return new ResponseEntity<>(Map.of("error","ACCESS DENIED:only bussiness_admin can get"), HttpStatus.FORBIDDEN);
//                    }
//
//                    String eSplits[]=decodedEid.split("/bussinessunits\\?buid=");
//                    String enterpriseId=eSplits[0].trim();
//
//                    String bSplits[]=eSplits[1].split("/regions");
//                    String bussinessUnitId=bSplits[0].trim();
//
////                    RegionDto regionDto=objectMapper.convertValue(requiredBody, RegionDto.class);
//                    List<RegionDto> regions= regionService.getRegions(enterpriseId,bussinessUnitId);
//                    return ResponseEntity.ok(regions);
//
//                }



                //..........read all employess under eid and buid......................................

//                else if( decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/employees")){
//                    return personalManagementController.getallEmployees(decodedEid,loggedInUser,userPrinciple);
//                }

//                //..........read all employess under eid ......................................

//                else if( decodedEid.contains("/employees")){
//                    return personalManagementController.getallEmployee(decodedEid,loggedInUser,userPrinciple);
//                }

//                // --- get product item---------- ---
//                else if (decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/productgroup?pgid=") && decodedEid.contains("/product_type?ptid=") && decodedEid.contains("/product_sku")){
//                    return productCatalogueController.getProductSku(decodedEid,loggedInUser,userPrinciple);
//                }
//
//
//
//                // --- get product Type---------- ---
//                else if (decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/productgroup?pgid=") && decodedEid.contains("/product_type")){
//                    return productCatalogueController.getProductTypes(decodedEid,loggedInUser,userPrinciple);
//                }
//
//                // --- get product Group---------- ---
//                else if (decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/productgroup")){
//                    return productCatalogueController.getProductGroups(decodedEid,loggedInUser,userPrinciple);
//                }

//
//                //...get service item..............................................................
//                else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/service_group?sgid=") && decodedEid.contains("/service_type?stid") && decodedEid.contains("/service_item")){
//                    return serviceCatalougeController.getServiceItems(loggedInUser,userPrinciple,decodedEid);
//                }
//
//
//                // --- get Service Types---------- ------------------
//                else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/service_group?sgid=") && decodedEid.contains("/service_type")){
//                    return serviceCatalougeController.getServiceTypes(loggedInUser,userPrinciple,decodedEid);
//                }
//
//                // --- get Service------------ Group---------- ----------------------
//                else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/service_group")){
//                    return serviceCatalougeController.getServiceGroup(loggedInUser,userPrinciple,decodedEid);
//                }


//               ///.....get customer.......................................................

//               else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/customer")){
//                   return customerController.getCustomers(decodedEid,userPrinciple,loggedInUser);
//                }


               //get all leads..........................................................................


//                else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/leads")){
//                    return leadsController.getLeads(decodedEid,userPrinciple,loggedInUser);
//                }




                //get all teriitories under eid and buid..........................................................................


//                else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/territories")){
//                    return teritoryController.getTerritories(decodedEid,userPrinciple,loggedInUser);
//                }



                //              ...................// bussinessunits read  by id......................................

////
//                else if (decodedEid.contains("/bussinessunits?buid=")) {
//                    if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "ENTERPRISE_ADMIN") && !hasRole(userPrinciple, "HR MANAGER")){
//                        return new ResponseEntity<>(Map.of("error" ,"ACCESS DENIED :only BUSSINESS ADMIN CAN GET"),HttpStatus.FORBIDDEN);
//                    }
//                    // 1/bussinessunits?buid=2
//                    String[] bSplits=decodedEid.split("/bussinessunits\\?buid=");
//                    String enterpriseId=bSplits[0].trim();
//                    String bussinessId=bSplits[1].trim();
//
//                    BussinessUnitDto busniess=bussinessUnitService.getBussinessByIdEnterpriseById(enterpriseId,bussinessId);
//                    return ResponseEntity.ok(busniess);
//
//                }
//






                //              ...................// bussinessunits read........................................

//                else if (decodedEid.contains("/bussinessunits")) {
//                    if (!hasRole(userPrinciple, "BUSINESS_ADMIN") && !hasRole(userPrinciple, "ENTERPRISE_ADMIN") &&!hasRole(userPrinciple, "HR MANAGER")) {
//                        return new ResponseEntity<>(Map.of("error", "ACCESS DENIED :only BUSSINESS ADMIN CAN GET"), HttpStatus.FORBIDDEN);
//                    }
//
//                    String[] bSplits=decodedEid.split("/bussinessunits");
//                    String enterpriseId=bSplits[0].trim();
//
//                    List<BussinessUnitDto> busniess=bussinessUnitService.getBussinessEnterpriseById(enterpriseId, loggedInUser, userPrinciple);
//                    return ResponseEntity.ok(busniess);
//
//                }

//                else {
//                    // Extract enterpriseId safely from decodedEid
//                    // decodedEid may be "1" or "1/" or "1/enterprises"
//
//                    String[] parts = decodedEid.split("/");
//                    String enterpriseId = parts[0].trim(); // Keep it as String
//
//                    // Fetch enterprise by String ID
//                    EnterpriseDto dto = enterpriseService.getenterpriseByEid(enterpriseId);
//
//                    return ResponseEntity.ok(dto);
//
//                }

//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error invalid urls");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(Map.of("error", "Error: " + e.getMessage()));
//
//            }


//       }
//

}