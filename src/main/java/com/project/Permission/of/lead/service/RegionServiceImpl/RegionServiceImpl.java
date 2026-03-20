package com.project.Permission.of.lead.service.RegionServiceImpl;

import com.project.Permission.of.lead.dto.RegionDto;
import com.project.Permission.of.lead.entity.BussinessUnit;
import com.project.Permission.of.lead.entity.Enterprise;
import com.project.Permission.of.lead.entity.Region;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.mapper.RegionMapper;
import com.project.Permission.of.lead.repository.BussinessUnitRepository;
import com.project.Permission.of.lead.repository.EnterpriseRepostory;
import com.project.Permission.of.lead.repository.RegionRepository;
import com.project.Permission.of.lead.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import java.util.List;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository regionRepo;

    @Autowired
    private BussinessUnitRepository bussinessRepo;

    @Autowired
    private EnterpriseRepostory enterpriseRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @PreAuthorize("hasRole('BUSSINESS_ADMIN')")
    public RegionDto createRegions(RegionDto regionDto,String eid, String buid, Users loggedInUser) {

      Enterprise enterprise= enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("Enterprise  not exists"));

     BussinessUnit bussinessUnit=  bussinessRepo.findByBuid(buid)
                .orElseThrow(()-> new RuntimeException("BussinessUnit not exists"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit don't belong to the enterprise");
        }

        String rid=jdbcTemplate.queryForObject(
                "SELECT create_entity_id(?)",
                new Object[]{"REGION"},
                String.class
        );
        System.out.println("Generated Rid ID: " + rid);

        Region region= RegionMapper.mapToRegion(regionDto,loggedInUser.getUid(),null,buid);
        region.setActive(true);
        region.setRid(rid);
        Region saved=regionRepo.save(region);
        return RegionMapper.mapToRegionDto(saved);
    }


    //    ....................// Get Regions//...............................................

    @Override
    public List<RegionDto> getRegions(String eid, String buid) {


            Enterprise enterprise=enterpriseRepo.findByEid(eid).
                    orElseThrow(()-> new RuntimeException("Enterprise  not exists"));

            BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid)
                    .orElseThrow(()->new RuntimeException("BussinessUnit not exists"));

            if(!bussinessUnit.getEnterpriseId().equals(eid)){
                throw new RuntimeException("bussiness unit don't belong to the enterprise");
            }

        List<Region> regions = regionRepo.findByEnterpriseAndBussinessUnit(eid,buid);

        List<RegionDto> regionDtos = regions.stream()
                .map(region -> RegionMapper.mapToRegionDto(region))
                .collect(Collectors.toList());

           return regionDtos;

        }


    //...get regiion by rid.........................................................

    @Override
    public RegionDto getRegionById(String eid, String buid, String rid) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise  not exists"));

        BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid)
                .orElseThrow(()->new RuntimeException("BussinessUnit not exists"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit don't belong to the enterprise");
        }

        Region region=regionRepo.findByRid(rid).
                orElseThrow(()->new RuntimeException("Region  not exists"));

        if(!region.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("region unit don't belong to the bussinessunit");
        }
        RegionDto regionDto=RegionMapper.mapToRegionDto(region);
        return regionDto;
    }



    //        ...................Update Regions................................................

    @Override
    public RegionDto updateRegion(RegionDto regionDto, Users loggedUser, String eid, String buid, String rid) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise  not exists"));

        BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid).
                orElseThrow(()->new RuntimeException("bussiness unit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to the enterprise");
        }

        Region region=regionRepo.findByRid(rid).
                orElseThrow(()->new RuntimeException("Region  not exists"));

        if(!region.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("bussiness unit dont belong to that region");
        }

        region.setName(regionDto.getName());
        if(regionDto.getName()!=null){
            region.setName(regionDto.getName());
        }

        if(regionDto.getDescription()!=null){
            region.setDescription(regionDto.getDescription());
        }


//        if(regionDto.isActive()!=null){
//            region.setActive(regionDto.isActive());
//        }
//        region.setDescription(regionDto.getDescription());
        region.setActive(regionDto.isActive());
        region.setUpdatedAt(LocalDateTime.now());
        region.setUpdatedBy(loggedUser.getUid());

        Region updated=regionRepo.save(region);
        return RegionMapper.mapToRegionDto(updated);


    }


//    GET alll regions..........................................................


    @Override
    public List<RegionDto> findAllRegion() {
        List<Region> regions = regionRepo.findAll();
        List<RegionDto> regionDtos= regions.stream().map(region ->RegionMapper.mapToRegionDto(region)).collect(Collectors.toList());
        return regionDtos;
    }





//    @Override
//    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
//    public void deleteRegion(Long id) {
//
//        Region r=regionRepo.findById(id).
//                orElseThrow(()-> new RuntimeException("Region Not Found"));
//
//        regionRepo.delete(r);
//
//    }



//    @Override
//    public List<RegionDto> getAllRegions() {
//       List<Region> regions=regionRepo.findAll();
//       return  regions.stream().map(region -> RegionMapper.mapToRegionDto(region)).collect(Collectors.toList());
//    }
//
//    @Override
//    public RegionDto getRegionById(Long id) {
//       Region region=regionRepo.findById(id).
//               orElseThrow(()-> new RuntimeException("Region Not Found"));
//       return RegionMapper.mapToRegionDto(region);
//    }
}
