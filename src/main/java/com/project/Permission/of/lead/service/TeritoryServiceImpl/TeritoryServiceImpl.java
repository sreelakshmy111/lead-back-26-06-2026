package com.project.Permission.of.lead.service.TeritoryServiceImpl;

import com.project.Permission.of.lead.dto.TeritoryDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.TeritoryMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.TeritoryService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeritoryServiceImpl implements TeritoryService {

    @Autowired
    private EnterpriseRepostory enterpriseRepo;

    @Autowired
    private BussinessUnitRepository bussinessRepo;

    @Autowired
    private RegionRepository regionRepo;

    @Autowired
    private CountryRepository countryRepo;

    @Autowired
    private ZoneRepository zoneRepo;

    @Autowired
    private StateRepository stateRepo;


    @Autowired
    private DistrictRepository districtRepo;



    @Autowired
    private TeritoryRepoitory teritoryRepo;


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
    public TeritoryDto createTeritory(TeritoryDto teritoryDto, Users loggedInUser, String did, String sid, String zid, String cid, String rid, String buid, String eid) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid)
                .orElseThrow(()->new RuntimeException("Bussiness not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussinessunit dont belong to this enterprise");
        }

        Region region=regionRepo.findByRid(rid)
                .orElseThrow(()->new RuntimeException("region not found"));

        if(!region.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("bussinessunit dont belong to this region");
        }

        Country country=countryRepo.findByCid(cid)
                .orElseThrow(()->new RuntimeException("country not found"));

        if(!country.getRegionId().equals(rid)){
            throw new RuntimeException("county dont belong to this region");
        }

        Zone zone=zoneRepo.findByZid(zid)
                .orElseThrow(()->new RuntimeException("zone not found"));

        if(!zone.getCountryId().equals(cid)){
            throw new RuntimeException("zone dont belong to this country");
        }

        State state=stateRepo.findBySid(sid)
                .orElseThrow(()->new RuntimeException("state not found"));

        if(!state.getCountryId().equals(cid)){
            throw new RuntimeException("state dont belong to this country");
        }

        District district=districtRepo.findByDid(did)
                .orElseThrow(()->new RuntimeException("district not found"));

        if(!district.getStateId().equals(sid)){
            throw new RuntimeException("district dont belong to this state");
        }

        String tid=jdbcTemplate.queryForObject(
                "SELECT create_entity_id(?)",
                new Object[]{"TERRITORY"},
                String.class
        );

        System.out.println("Generated territory ID: " + tid);

        Teritory teritory= TeritoryMapper.mapToTeritory(teritoryDto,loggedInUser.getUser_id(),null,did,sid,zid,cid,rid,buid);
        teritory.setActive(true);
        teritory.setTid(tid);
        Teritory saved=teritoryRepo.save(teritory);
        return TeritoryMapper.mapToTeritoryDto(saved);


    }

    @Override
    public List<TeritoryDto> getTeritories(String did, String sid, String zid, String cid, String rid, String buid, String eid) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("Enterprise not found"));

        BussinessUnit   bussinessUnit=bussinessRepo.findByBuid(buid).orElseThrow(
                ()-> new RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to te enterprise");
        }

        Region region=regionRepo.findByRid(rid)
                .orElseThrow(()-> new RuntimeException("Region not found"));

        if(!region.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("region don't belong to bussinessUnit");
        }

        Country country=countryRepo.findByCid(cid)
                .orElseThrow(()->new RuntimeException("Country not found"));

        if(!country.getRegionId().equals(rid)){
            throw new RuntimeException("country don't belong to region");
        }


        Zone zone=zoneRepo.findByZid(zid).
                orElseThrow(()-> new  RuntimeException("zone not found"));
        if(!zone.getCountryId().equals(cid)){
            throw new RuntimeException("zone not belong to country");
        }


        State state=stateRepo.findBySid(sid)
                .orElseThrow(()-> new RuntimeException("State not found"));

        if(!state.getZoneId().equals(zid)){
            throw new RuntimeException("state not belong to zone");
        }

        District district=districtRepo.findByDid(did).
                orElseThrow(()->new RuntimeException("district not found"));

        if(!district.getStateId().equals(sid)){
            throw new RuntimeException("district not belong to state");
        }

        List<Teritory> teritories=teritoryRepo.findByDistrictIdAndStateIdAndZoneIdAndCountryIdAndRegionIdAndBussinessUnitId(did,sid,zid,cid,rid,buid);
        List<TeritoryDto> teritoryDtos=teritories.stream().map(teritory->TeritoryMapper.mapToTeritoryDto(teritory)).collect(Collectors.toList());
        return teritoryDtos;

    }

///GET Territories by id....................................................................

//
//
//@Override
//public TeritoryDto getTeritorieById(String did,String sid,String zid, String cid ,String rid,String buid, String eid) {
//
//    Enterprise enterprise=enterpriseRepo.findByEid(eid)
//            .orElseThrow(()-> new RuntimeException("Enterprise not found"));
//
//    BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid).orElseThrow(
//            ()-> new RuntimeException("BussinessUnit not found"));
//
//    if(!bussinessUnit.getEnterpriseId().equals(eid)){
//        throw new RuntimeException("bussiness unit not belong to te enterprise");
//    }
//
//    Region region=regionRepo.findByRid(rid)
//            .orElseThrow(()-> new RuntimeException("Region not found"));
//
//    if(!region.getBussinessUnitId().equals(buid)){
//        throw new RuntimeException("region don't belong to bussinessUnit");
//    }
//
//    Country country=countryRepo.findByCid(cid)
//            .orElseThrow(()->new RuntimeException("Country not found"));
//
//    if(!country.getRegionId().equals(rid)){
//        throw new RuntimeException("country don't belong to region");
//    }
//
//
//    Zone zone=zoneRepo.findByZid(zid).
//            orElseThrow(()-> new  RuntimeException("zone not found"));
//    if(!zone.getCountryId().equals(cid)){
//        throw new RuntimeException("zone not belong to country");
//    }
//
//
//    State state=stateRepo.findBySid(sid)
//            .orElseThrow(()-> new RuntimeException("State not found"));
//
//    if(!state.getZoneId().equals(zid)){
//        throw new RuntimeException("state not belong to zone");
//    }
//
//    District district=districtRepo.findByDid(did).
//            orElseThrow(()->new RuntimeException("district not found"));
//
//    if(!district.getStateId().equals(sid)){
//        throw new RuntimeException("district not belong to state");
//    }
//
//    Teritory teritory=teritoryRepo.findByTid(tid).
//            orElseThrow(()-> new RuntimeException("teritory not found"));
//
//    if(!teritory.getDistrictId().equals(did)){
//
//        throw new RuntimeException("teritory don't belong to District");
//    }
//    TeritoryDto teritoryDto=TeritoryMapper.mapToTeritoryDto(teritory);
//    return teritoryDto;
//}
//

    @Override
    public TeritoryDto getTeritorieById(String did, String sid, String zid, String cid, String rid, String buid, String eid, String tid) {
        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid).orElseThrow(
                ()-> new RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to te enterprise");
        }

        Region region=regionRepo.findByRid(rid)
                .orElseThrow(()-> new RuntimeException("Region not found"));

        if(!region.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("region don't belong to bussinessUnit");
        }

        Country country=countryRepo.findByCid(cid)
                .orElseThrow(()->new RuntimeException("Country not found"));

        if(!country.getRegionId().equals(rid)){
            throw new RuntimeException("country don't belong to region");
        }


        Zone zone=zoneRepo.findByZid(zid).
                orElseThrow(()-> new  RuntimeException("zone not found"));
        if(!zone.getCountryId().equals(cid)){
            throw new RuntimeException("zone not belong to country");
        }


        State state=stateRepo.findBySid(sid)
                .orElseThrow(()-> new RuntimeException("State not found"));

        if(!state.getZoneId().equals(zid)){
            throw new RuntimeException("state not belong to zone");
        }

        District district=districtRepo.findByDid(did).
                orElseThrow(()->new RuntimeException("district not found"));

        if(!district.getStateId().equals(sid)){
            throw new RuntimeException("district not belong to state");
        }

        Teritory teritory=teritoryRepo.findByTid(tid).
                orElseThrow(()-> new RuntimeException("teritory not found"));

        if(!teritory.getDistrictId().equals(did)){

            throw new RuntimeException("teritory don't belong to District");
        }
        TeritoryDto teritoryDto=TeritoryMapper.mapToTeritoryDto(teritory);
        return teritoryDto;
    }


//   ............Update......................................................................


    @Override
    public TeritoryDto updateTeritory(TeritoryDto teritoryDto, Users loggedUser, String tid, String did, String sid, String zid, String cid, String rid, String buid, String eid) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("Enterprise not found"));

        BussinessUnit   bussinessUnit=bussinessRepo.findByBuid(buid).orElseThrow(
                ()-> new RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to te enterprise");
        }

        Region region=regionRepo.findByRid(rid)
                .orElseThrow(()-> new RuntimeException("Region not found"));

        if(!region.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("region don't belong to bussinessUnit");
        }

        Country country=countryRepo.findByCid(cid)
                .orElseThrow(()->new RuntimeException("Country not found"));

        if(!country.getRegionId().equals(rid)){
            throw new RuntimeException("country don't belong to region");
        }


        Zone zone=zoneRepo.findByZid(zid).
                orElseThrow(()-> new  RuntimeException("zone not found"));
        if(!zone.getCountryId().equals(cid)){
            throw new RuntimeException("zone not belong to country");
        }


        State state=stateRepo.findBySid(sid)
                .orElseThrow(()-> new RuntimeException("State not found"));

        if(!state.getZoneId().equals(zid)){
            throw new RuntimeException("state not belong to zone");
        }

        District district=districtRepo.findByDid(did).
                orElseThrow(()->new RuntimeException("district not found"));

        if(!district.getStateId().equals(sid)){
            throw new RuntimeException("district not belong to state");
        }

        Teritory teritory=teritoryRepo.findByTid(tid).
                orElseThrow(()-> new RuntimeException("teritory not found"));

        if(!teritory.getDistrictId().equals(did)){

            throw new RuntimeException("teritory don't belong to District");
        }

//        teritory.setName(teritoryDto.getName());

        if(teritoryDto.getName()!=null){
            teritory.setName(teritoryDto.getName());
        }
//        teritory.setDescription(teritoryDto.getDescription());

        if(teritoryDto.getName()!=null){
            teritory.setDescription(teritoryDto.getDescription());
        }

        teritory.setActive(teritoryDto.isActive());
        teritory.setUpdatedAt(LocalDateTime.now());
        teritory.setUpdatedBy(loggedUser.getUser_id());

        Teritory updated=teritoryRepo.save(teritory);
        return TeritoryMapper.mapToTeritoryDto(updated);
    }



    @Override
    public List<TeritoryDto> getTerritorriesByBuidAndEid(String eid, String buid, UserPrinciple userPrinciple, Users loggedInUser) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("Enterprise not found"));

        BussinessUnit   bussinessUnit=bussinessRepo.findByBuid(buid).orElseThrow(
                ()-> new RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to te enterprise");
        }

        List<Teritory> teritory=teritoryRepo.findByBussinessUnitId(buid);

        return teritory.stream().map(t-> TeritoryMapper.mapToTeritoryDto(t)).collect(Collectors.toUnmodifiableList());

    }



//    @Override
//    public List<TeritoryDto> getAll() {
//        List<Teritory> territory = (List<Teritory>) teritoryRepo.findAll(); // works if repo is JpaRepository
//        return territory.stream()
//                .map(TeritoryMapper::mapToTeritoryDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public TeritoryDto getTeritoryById(Long id) {
//        Teritory teritory=teritoryRepo.findById(id)
//                .orElseThrow(()-> new RuntimeException("teritory not exists"));
//        return TeritoryMapper.mapToTeritoryDto(teritory);
//    }
}
