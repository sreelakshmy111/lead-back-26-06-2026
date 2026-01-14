package com.project.Permission.of.lead.service.ZoneServiceImpl;


import com.project.Permission.of.lead.dto.ZoneDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.ZoneMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

@Autowired
    private EnterpriseRepostory enterpriseRepo;

    @Autowired
    private BussinessUnitRepository bussinessUnitRepo;

    @Autowired
    private RegionRepository regionRepo;

    @Autowired
    private CountryRepository countryRepo;

    @Autowired
    private ZoneRepository zoneRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ZoneDto createZone(ZoneDto zoneDto, String eid, String buid, String rid, String cid, Users loggedInUser) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("Enterprise not found"));

        BussinessUnit   bussinessUnit=bussinessUnitRepo.findByBuid(buid).orElseThrow(
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

        String zid=jdbcTemplate.queryForObject(
                "SELECT create_entity_id(?)",
                new Object[]{"ZONE"},
                String.class
         );

        System.out.println("Generated Zone ID: " + zid);

        Zone zone= ZoneMapper.mapToZone(zoneDto,loggedInUser.getUser_id(),null,cid,rid,buid);
        zone.setZid(zid);
        zone.setActive(true);
        Zone savedZone=zoneRepo.save(zone);
        return ZoneMapper.mapToZoneDto(savedZone);

    }



//    read zones.............................................

    @Override
    public List<ZoneDto> getZones(String cid, String rid, String buid, String eid) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepo.findByBuid(buid).
                orElseThrow(()-> new  RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to enterprise");
        }

        Region region=regionRepo.findByRid(rid).
                orElseThrow(()->new RuntimeException("Region not found"));

        if(!region.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("region don't belong to bussinessUnit");
        }

        Country country=countryRepo.findByCid(cid).
                orElseThrow(()->new RuntimeException("Country not found"));

        if(!country.getRegionId().equals(rid)){
            throw new RuntimeException("country don't belong to region");
        }

        List<Zone> zones=zoneRepo.findByCountryIdAndRegionIdAndBussinessUnitId(cid,rid,buid);
        List<ZoneDto> zonesDtos = zones.stream().map(zone -> ZoneMapper.mapToZoneDto(zone)).collect(Collectors.toList());
        return zonesDtos;


    }

//.get zones by id.............................................................

    @Override
    public ZoneDto getZonesById(String cid, String rid, String buid, String eid, String zid) {
        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepo.findByBuid(buid).
                orElseThrow(()-> new  RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to enterprise");
        }

        Region region=regionRepo.findByRid(rid).
                orElseThrow(()->new RuntimeException("Region not found"));

        if(!region.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("region don't belong to bussinessUnit");
        }

        Country country=countryRepo.findByCid(cid).
                orElseThrow(()->new RuntimeException("Country not found"));

        if(!country.getRegionId().equals(rid)){
            throw new RuntimeException("country don't belong to region");
        }

        Zone zone=zoneRepo.findByZid(zid).orElseThrow(()-> new RuntimeException("Zone not found"));

        if(!zone.getCountryId().equals(cid)){
            throw new RuntimeException("zone don't defines in this country");
        }

        ZoneDto zoneDto=ZoneMapper.mapToZoneDto(zone);
        return zoneDto;
    }



//    ....................update//...............................

    @Override
    public ZoneDto updateZones(ZoneDto zoneDto, Users loggedUser, String zid, String cid, String rid, String buid, String eid) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("Enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepo.findByBuid(buid).
                orElseThrow(()-> new  RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to enterprise");
        }

        Region region=regionRepo.findByRid(rid).
                orElseThrow(()->new RuntimeException("Region not found"));

        if(!region.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("region don't belong to bussinessUnit");
        }

        Country country=countryRepo.findByCid(cid).
                orElseThrow(()->new RuntimeException("Country not found"));

        if(!country.getRegionId().equals(rid)){
            throw new RuntimeException("country don't belong to region");
        }

        Zone zone=zoneRepo.findByZid(zid).orElseThrow(()-> new RuntimeException("Zone not found"));

        if(!zone.getCountryId().equals(cid)){
            throw new RuntimeException("zone don'tdefines in this country");
        }

//        zone.setName(zoneDto.getName());

        if(zoneDto.getName()!=null){
            zone.setName(zoneDto.getName());
        }
//        zone.setDescription(zoneDto.getDescription());

        if(zoneDto.getDescription()!=null){
            zone.setDescription(zoneDto.getDescription());
        }
        zone.setActive(zoneDto.isActive());
        zone.setUpdatedAt(LocalDateTime.now());
        zone.setUpdatedBy(loggedUser.getUser_id());

        Zone updateZone=zoneRepo.save(zone);
        return ZoneMapper.mapToZoneDto(updateZone);

    }

    @Override
    public List<ZoneDto> getAllZones() {
        List<Zone> zones=zoneRepo.findAll();
        List<ZoneDto> zoneDtos=zones.stream().map(zone->ZoneMapper.mapToZoneDto(zone)).collect(Collectors.toList());
        return zoneDtos;
    }

//    @Override
//    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
//    public ZoneDto createZone(ZoneDto zoneDto) {
//
//        BussinessUnit be=bussinessRepo.findById(zoneDto.getBussinessEnterpriseId())
//                .orElseThrow(()-> new RuntimeException("bussiness not found"));
//
//
//        Region region=regionRepo.findById(zoneDto.getRegionId())
//                .orElseThrow(()->new RuntimeException("region not found"));
//
//        Country c=countryRepo.findById(zoneDto.getCountryId())
//                .orElseThrow(()->new RuntimeException("country not found"));
//
//
//        Zone zone= ZoneMapper.mapToZone(zoneDto, c,region, be);
//        Zone saved=zoneRepo.save(zone);
//        return ZoneMapper.mapToZoneDto(saved);
//
//
//    }

//    @Override
//    public List<ZoneDto> getZones() {
//        List<Zone> zones = zoneRepo.findAll();
//        return zones.stream().map(zone -> ZoneMapper.mapToZoneDto(zone)).collect(Collectors.toList());
//    }

//    @Override
//    public ZoneDto getZoneById(long id) {
//
//        Zone zone=zoneRepo.findById(id).
//                orElseThrow(()->new  RuntimeException("country not exists"));
//
//        return ZoneMapper.mapToZoneDto(zone);
//
//    }
}
