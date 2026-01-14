package com.project.Permission.of.lead.service.DistrictServiceImpl;

import com.project.Permission.of.lead.dto.DistrictDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.DistrictMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DistrictServiceImpl implements DistrictService {

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
    private JdbcTemplate jdbcTemplate;

    @Override
    public DistrictDto createDistrict(DistrictDto districtDto, Users loggedInUser, String sid, String zid, String cid, String rid, String buid, String eid) {

       Enterprise enterprise=enterpriseRepo.findByEid(eid).
               orElseThrow(()-> new RuntimeException("Enterprise not found"));

       BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid).
               orElseThrow(()-> new RuntimeException("BussinesUnit not found"));


       if(!bussinessUnit.getEnterpriseId().equals(eid)){
           throw  new RuntimeException("bussiness unit not belong to the enterprise");

       }

       Region region=regionRepo.findByRid(rid).
               orElseThrow(()-> new RuntimeException("Region not found"));

       if(!region.getBussinessUnitId().equals(buid)){
           throw new RuntimeException("region not belong to the bussinessUnit");
       }

       Country country=countryRepo.findByCid(cid).
               orElseThrow(()->new RuntimeException("Country not found"));

       if(!country.getRegionId().equals(rid)){
           throw  new RuntimeException("country not belong to the region");
       }

       Zone zone=zoneRepo.findByZid(zid).
               orElseThrow(()-> new RuntimeException("Zone not found"));

       if(!zone.getCountryId().equals(cid)){
           throw  new RuntimeException("zone not belong to the country");
       }

       State state=stateRepo.findBySid(sid).
               orElseThrow(()-> new RuntimeException("State not found"));

       if(!state.getZoneId().equals(zid)){
           throw  new RuntimeException("state not belong to the zone");
       }

       String did=jdbcTemplate.queryForObject(
               "SELECT create_entity_id(?)",
               new Object[]{"DISTRICT"},
               String.class
       );

        System.out.println("Generated District ID: " +did);

       District district=DistrictMapper.mapToDistrict(districtDto,loggedInUser.getUser_id(),null,sid,zid,cid,rid,buid);
       district.setDid(did);
       District savedDistrict=districtRepo.save(district);
       return DistrictMapper.mapToDistrictDto(savedDistrict);

    }


//    fetch all districtsss..................................................................


    @Override
    public List<DistrictDto> getDistricts(String sid, String zid, String cid, String rid, String buid, String eid) {

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


        List<District> districts=districtRepo.findByStateIdAndZoneIdAndCountryIdAndRegionIdAndBussinessUnitId(sid,zid,cid,rid,buid);
        List<DistrictDto> districtDtos=districts.stream().map(district -> DistrictMapper.mapToDistrictDto(district)).collect(Collectors.toList());
        return districtDtos;
    }


 /// get districts by id..................................................................


 @Override
 public DistrictDto getDistrictById(String did, String sid, String zid, String cid, String rid, String buid, String eid) {

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
             orElseThrow(()->new RuntimeException("District not found"));

     if(!district.getStateId().equals(sid)){
         throw new RuntimeException("district not belong to state");
     }

     DistrictDto districtDto=DistrictMapper.mapToDistrictDto(district);
     return districtDto;
 }



//    .............update..........................................................

    @Override
    public DistrictDto updateDistrict(DistrictDto districtDto, Users loggedUser, String did, String sid, String zid, String cid, String rid, String buid, String eid) {


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
                orElseThrow(()->new RuntimeException("District not found"));

        if(!district.getStateId().equals(sid)){
            throw new RuntimeException("district not belong to state");
        }

//        district.setName(districtDto.getName());

        if(districtDto.getName()!=null){
            district.setName(districtDto.getName());
        }
//        district.setDescription(districtDto.getDescription());

        if(districtDto.getDescription()!=null){
            district.setDescription(districtDto.getDescription());
        }
        district.setActive(districtDto.isActive());
        district.setUpdatedAt(LocalDateTime.now());
        district.setUpdatedBy(loggedUser.getUser_id());

        District updated=districtRepo.save(district);
        return DistrictMapper.mapToDistrictDto(updated);


    }

    @Override
    public List<DistrictDto> getAllDistricts() {
        List<District> districts=districtRepo.findAll();
        List<DistrictDto> districtDtos=districts.stream().map(DistrictMapper::mapToDistrictDto).collect(Collectors.toList());
        return districtDtos;
    }

//    public List<DistrictDto> getAllDistricts() {
//        List<District> districts = districtRepo.findAll();
//        return districts.stream()
//                .map(district -> DistrictMapper.mapToDistrictDto(district))
//                .collect(Collectors.toList());
//    }

//    @Override
//    public DistrictDto getDistrictById(long id) {
//        District district=districtRepo.findById(id)
//                .orElseThrow(()-> new RuntimeException("district not exits"));
//        return DistrictMapper.mapToDistrictDto(district);
//
//    }


//    public List<DistrictDto> getAllRegions() {
//        List<Region> districts=regionRepo.findAll();
//        return  districts.stream().map(district-> DistrictMapper.mapToDistrictDto(districts)).collect(Collectors.toList());
//    }
}
