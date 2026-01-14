package com.project.Permission.of.lead.controller.Enterprise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Permission.of.lead.controller.CustomerController;
import com.project.Permission.of.lead.controller.LeadsController;
import com.project.Permission.of.lead.controller.PersonalManagementController;
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
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

//
//@RestController
//@RequestMapping("/enterprises")
public class HierrachyUpdateController {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private BussinessUnitService bussinessUnitService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private StateService stateService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private TeritoryService teritoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PersonalManagementController personalManagementController;


    @Autowired
    private CustomerController customerController;

    @Autowired
    private LeadsController leadsController;

    private boolean hasRole(UserPrinciple userPrinciple,String role) {
        return userPrinciple.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" +role));
    }

    @PutMapping(params = "eid")
    public ResponseEntity<?> updateHierachy(
            @RequestParam("eid") String eidParam,
            @AuthenticationPrincipal UserPrinciple userPrinciple,
            @RequestBody  Map<String,Object> requestBody)

//       ..................//UPDATE//..........................................................

        {

            String decodedEid= URLDecoder.decode(eidParam, StandardCharsets.UTF_8);
            Users loggedUser=userPrinciple.getUser();


        try {


//            ...............Teritory update//.................................................

            if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
                    && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=")
                    && decodedEid.contains("/states?sid=") && decodedEid.contains("/districts?did=") && decodedEid.contains("/teritories?tid=")){
                if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED ONLY FOR BUSINESS ADMIN");
                }

                String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");   //  [1, 2/regions?rid=2/countries?cid=3/zones?zid=2/states?sid=1/districts?did=1/teritories?tid=1]
                String enterpriseId= eSplits[0].trim();

                String [] bSplits=eSplits[1].split("/regions\\?rid=");   //  [ 2,2/countries?cid=3/zones?zid=2/states?sid=1/districts?did=1/teritories?tid=1]
                String bussinessUnitId= bSplits[0].trim();

                String [] rSplits=bSplits[1].split("/countries\\?cid=");  //  [ 2,  3/zones?zid=2/states?sid=1/districts?did=1/teritories?tid=1]
                String regionId= rSplits[0].trim();

                String [] cSplits=rSplits[1].split("/zones\\?zid=");              //  [   3,2/states?sid=1/districts?did=1/teritories?tid=1]
                String countryId= cSplits[0].trim();

                String [] zSplits= cSplits[1].split("/states\\?sid=");             //  [  2,1/districts?did=1/teritories?tid=1]
                String zoneId= zSplits[0].trim();

                String [] sSplits=zSplits[1].split("/districts\\?did=");     //  [  1,1/teritories?tid=1]
                String stateId= sSplits[0].trim();

                String [] dSplits=sSplits[1].split("/teritories\\?tid=");  //  [  1,1]
                String districtId=dSplits[0].trim();

                String teritoryId=dSplits[1].trim();




                TeritoryDto teritoryDto=objectMapper.convertValue(requestBody, TeritoryDto.class);

                TeritoryDto updated=teritoryService.updateTeritory(teritoryDto,loggedUser,teritoryId,districtId,stateId,zoneId,countryId,regionId,bussinessUnitId,enterpriseId);
                return ResponseEntity.status(HttpStatus.OK).body(updated);
            }



//            .................//District update...............................................

            else if(decodedEid.contains("/bussinessunits?buid=")&& decodedEid.contains("/regions?rid=")
                    && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=")
                    && decodedEid.contains("/states?sid=") && decodedEid.contains("/districts?did=")){
                if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACESS DENIED -ONLY BUSSINESS ADMIN CAN ACCESS");
                }

                //1/bussinessunits?buid=2/reions?rid=3/countries?cid=1/zones?zid=2/states?sid=4/district

                String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");   //  [1, 2/reions?rid=3/countries?cid=2/zones?zid=2/states?sid=4/district?did=4]
                String enterpriseId=eSplits[0].trim();

                String [] bSplits=eSplits[1].split("/regions\\?rid=");  //  [2, 3/countries?cid=2/zones?zid=2/states?sid=4/district?did=4]
                String bussinessUnitId=bSplits[0].trim();

                String[] rSplits=bSplits[1].split("/countries\\?cid=");     //  [ 3, 2/zones?zid=2/states?sid=4/district?did=4]
                String regionId=rSplits[0].trim();

                String[] cSplits=rSplits[1].split("/zones\\?zid=");         //  [  2,2/states?sid=4/district?did=4]
                String countryId=cSplits[0].trim();

                String [] zSplits=cSplits[1].split("/states\\?sid=");                   //  [  2,4/district?did=4]
                String zoneId= zSplits[0].trim();

                String [] sSplits=zSplits[1].split("/districts\\?did=");        //  [  4/district?did=4] ->  [  4,4]
                String stateId=sSplits[0].trim();

                String districtId=sSplits[1].trim();

                DistrictDto districtDto=objectMapper.convertValue(requestBody,DistrictDto.class);
                DistrictDto updated=districtService.updateDistrict(districtDto,loggedUser,districtId,stateId,zoneId,countryId,regionId,bussinessUnitId,enterpriseId);
                return ResponseEntity.ok().body(updated);

            }



//            ....................//State update............................................

            else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
                    && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=") && decodedEid.contains("/states?sid=")){
                if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED-Only forBUSSINESS ADMIN");

                }

                String [] eSplits=decodedEid.split("/bussinessunits\\?buid=") ;
                System.out.println("eSplits: " + Arrays.toString(eSplits));
                //    [1,  2/regions?rid=2/countries?cid=1?/zone?zid=5/state ]
                String enterpriseId=eSplits[0].trim();

                String [] bSplits=eSplits[1].split("/regions\\?rid=");    //    [ 2, 2/countries?cid=1?/zone?zid=5/state?sid=4 ]
                System.out.println("bSplits: " + Arrays.toString(bSplits));
                String bussinessUnitId=bSplits[0].trim();

                String [] rSplits=bSplits[1].split("/countries\\?cid=");   //    [  2,  4/zone?zid=5/state?sid=4 ]
                System.out.println("rSplits: " + Arrays.toString(rSplits));
                String regionId=rSplits[0].trim();

                String [] cSplits=rSplits[1].split("/zones\\?zid=");   //    [  4,  5/state?sid=4 ]
                System.out.println("cSplits: " + Arrays.toString(cSplits));
                String countryId=cSplits[0].trim();

                String [] zSplits=cSplits[1].split("/states\\?sid=");   //    [  5/state?sid=4 ] ->  [  5,4 ]

                System.out.println("zSplits: " + Arrays.toString(zSplits));
               String zoneId=zSplits[0].trim();

                String stateId=zSplits[1].trim();

                StateDto stateDto=objectMapper.convertValue(requestBody,StateDto.class);

                StateDto update=stateService.updateState(stateDto,loggedUser,stateId,zoneId,countryId,regionId,bussinessUnitId,enterpriseId);
                return ResponseEntity.status(HttpStatus.OK).body(update);

            }








//          ..........................// Zone update//........................................................

            if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")
                    && decodedEid.contains("/countries?cid=") && decodedEid.contains("/zones?zid=")){
                if(!hasRole(userPrinciple,"BUSINESS_ADMIN")){
                    return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("error-ACCESS DENIED :Requires role BUSSINESS_ADMIN role");

                }

                String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");     //    [1,2/regions?rid=2/countries?cid=1?/zone?zid=2]
                String enterpriseId=eSplits[0].trim();

                String [] bSplits=eSplits[1].split("/regions\\?rid=");   //   [2,2/countries?cid=1?/zone?zid=2]
                String bussinessunitId=bSplits[0].trim();

                String [] rSplits=bSplits[1].split("/countries\\?cid=");   //   [2,1/zone?zid=2]
                String regionId=rSplits[0].trim();

                String[] cSplits=rSplits[1].split("/zones\\?zid=");   //   [1,/zone?zid=2] ->  [1,2]
                String countryId=cSplits[0].trim();

                String zoneId=cSplits[1].trim();

                ZoneDto zoneDto = objectMapper.convertValue(requestBody, ZoneDto.class);
                ZoneDto updated=zoneService.updateZones(zoneDto,loggedUser,zoneId,countryId,regionId,bussinessunitId,enterpriseId);
                return ResponseEntity.status(HttpStatus.OK).body(updated);





            }




            //            .................//country update...............................................


           else if (decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=") && decodedEid.contains("/countries?cid=")) {

                if (!hasRole(userPrinciple, "BUSINESS_ADMIN")) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ACCESS DENIED:ONLY FOR BUSSINESS ADMIN");
                }


                String[] eSplits = decodedEid.split("/bussinessunits\\?buid="); // eplits=[4,3/regions?rid=2/countries]
                String enterpriseId = eSplits[0].trim();

                String[] bSplits = eSplits[1].split("/regions\\?rid=");
                String bussinessUnitId = bSplits[0].trim();

                String[] rSplits = bSplits[1].split("/countries\\?cid=");
                String regionId = rSplits[0].trim();

                String countryId = rSplits[1].trim();

                CountryDto countryDto = objectMapper.convertValue(requestBody, CountryDto.class);

                CountryDto countryDto1 = countryService.updateCountriesByHierarchyIds(countryDto, countryId, enterpriseId, bussinessUnitId, regionId, loggedUser);
                return ResponseEntity.status(HttpStatus.OK).body(countryDto1);



            }


            //            .................//region update...............................................


            else if (decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/regions?rid=")) {
                if (!hasRole(userPrinciple, "BUSINESS_ADMIN")) {
                    return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSINESS_ADMIN for Create."), HttpStatus.FORBIDDEN);
                }
//                1/bussinessunits?buid=2/regions?rid=2


                // 1. Parsing Region IDs
                String[] enterpriseSplit = decodedEid.split("/bussinessunits\\?buid=");    //   [   1,2/regions?rid=2]
                String enterpriseId = enterpriseSplit[0].trim();

                String[] buSplit = enterpriseSplit[1].split("/regions\\?rid=");    //   [   2,2]
                String businessUnitId =buSplit[0].trim();

                String  regionId = buSplit[1].trim();

                RegionDto regionDto=objectMapper.convertValue(requestBody, RegionDto.class);

                RegionDto updated=regionService.updateRegion(regionDto,loggedUser,enterpriseId,businessUnitId,regionId);
                return ResponseEntity.status(HttpStatus.OK).body(updated);



            }


//            else if( decodedEid.contains("/bussinessunits?buid=") &&  decodedEid.contains("/leads?lid=") && decodedEid.contains("employee")){
//                return leadsController.assignEmployee(decodedEid,requestBody,loggedUser,userPrinciple);
//            }

//            link addresss to bu.................................

//            else if (decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("addresses?aid=")) {
//
//                if(!hasRole(userPrinciple, "ENTERPRISE_ADMIN") &&  !hasRole(userPrinciple, "BUSINESS_ADMIN")) {
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesss Denied,onl for enterprise admin");
//                }
//
////                1/bussinessunits?buid=2/regions?rid=2
//
//                String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");  //  [  1,2/addresses?aid=2]
//                Long enterpriseId=Long.parseLong(eSplits[0]);
//
//                String [] aSplits=eSplits[1].split("/addresses\\?aid=");   //  [ 2,2]
//
//                Long bussiessunitId=Long.parseLong(aSplits[0]);
//
//
//                Long addressId = Long.parseLong(aSplits[1]);
//
//                bussinessUnitService.linkAddress(enterpriseId,bussiessunitId,addressId);
//                return ResponseEntity.status(HttpStatus.OK).body("linkedd");
//
//
//



//         }  assign emplouyee a bussiness units...................................
//
//              else if(decodedEid.contains("/employee?empid=") && decodedEid.contains("/bussinessunit?buid=")){
//                  return personalManagementController.assignBussinessUnit(decodedEid,requestBody,loggedUser,userPrinciple);
//            }

            //..............Assign employeees territories.........................

//            else if(decodedEid.contains("/employee?empid=") && decodedEid.contains("/territories")){
//                return personalManagementController.assignTerritories(decodedEid,requestBody,loggedUser,userPrinciple);
//            }

            // --- Check for Employee update first ----------------
//            else if ( decodedEid.contains("/employee?empid=")) {
//                return personalManagementController.updateEmployeeById(decodedEid, requestBody, loggedUser, userPrinciple);
//            }

            //..............Assign employeees territories.........................


            //        }  assign emplouyee to a lead...................................




            //...........contact update.........................................................


//            else if(decodedEid.contains("/bussinessunits?buid=") && decodedEid.contains("/customer?cuid=")){
//                return customerController.updateCustomerContact(requestBody,decodedEid,userPrinciple,loggedUser);
//            }



            //            .................//bussiness unit update...............................................


            else if (decodedEid.contains("/bussinessunits?buid=")) {

                if (!hasRole(userPrinciple, "BUSINESS_ADMIN")) {
                    return new ResponseEntity<>(Map.of("error", "Access Denied: Requires BUSINESS_ADMIN for update."), HttpStatus.FORBIDDEN);
                }

                 //                1/bussinessunits?buid=2

                String [] eSplits=decodedEid.split("/bussinessunits\\?buid=");      //            [1,2]
                String enterpriseId = eSplits[0].trim();
                String bussinessUnitId = eSplits[1].trim();

                BussinessUnitDto bussinessUnitDto=objectMapper.convertValue(requestBody, BussinessUnitDto.class);
               BussinessUnitDto update=bussinessUnitService.updateBussinessUnit(bussinessUnitDto,loggedUser,bussinessUnitId,enterpriseId);
                return ResponseEntity.status(HttpStatus.OK).body(update);



            }


            else {
                // Extract enterpriseId safely from decodedEid
                // decodedEid may be "1" or "1/" or "1/enterprises"
                String[] parts = decodedEid.split("/");
                String enterpriseId = parts[0].trim();

                EnterpriseDto enterpriseDto = objectMapper.convertValue(requestBody, EnterpriseDto.class);

                EnterpriseDto dto = enterpriseService.updateEnterprise(enterpriseId, enterpriseDto, loggedUser);

                return ResponseEntity.ok(dto);
            }

//

//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error invalid urls");


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error: " + e.getMessage()));
        }







//    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
//    @PostMapping("/")
//    public ResponseEntity<CountryDto> createCountry(@RequestBody CountryDto dto){
//
//        CountryDto countryDto =service.createCountry(dto);
//        return ResponseEntity.ok(countryDto);



    }

//
//    @GetMapping("")
//    public ResponseEntity<List<CountryDto>> getAllCountries(){
//        List<CountryDto> countryDtos=service.getAllCountries();
//        return ResponseEntity.ok(countryDtos);
//    }
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id){
//         CountryDto dto=service.getCountryById(id);
//         return ResponseEntity.ok(dto);
//
//    }
}
