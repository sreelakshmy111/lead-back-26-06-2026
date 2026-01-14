package com.project.Permission.of.lead.service.StateServiceImpl;

import com.project.Permission.of.lead.dto.StateDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.StateMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
public class StateServiceImpl implements StateService {

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
    private JdbcTemplate jdbcTemplate;




    @Override
    public StateDto createState(StateDto stateDto, Users loggedInUser, String zid, String cid, String rid, String buid, String eid) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()->new RuntimeException("enterprise not found"));

        BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid).orElseThrow(
                ()->new RuntimeException("bussinessUnit mot found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to the enterprise ");
        }

        Region region=regionRepo.findByRid(rid)
                .orElseThrow(()->new RuntimeException("region mot found"));

        if(!region.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("region not belong to the bussinessUnit ");
        }

        Country country=countryRepo.findByCid(cid).
                orElseThrow(()->new RuntimeException("country mot found"));

        if(!country.getRegionId().equals(rid)){
            throw new RuntimeException("country not belong to the region");
        }

        Zone zone=zoneRepo.findByZid(zid).
                orElseThrow(()-> new RuntimeException("zone mot found"));

        if(!zone.getCountryId().equals(cid)){
            throw new RuntimeException("zone not belong to the country");
        }

        String sid=jdbcTemplate.queryForObject(
                "SELECT create_entity_id(?)",
                new Object[]{"STATE"},
                String.class
        );
        System.out.println("Generated state ID: " + sid);

        State state= StateMapper.mapToState(stateDto,loggedInUser.getUser_id(),null,zid,cid,rid,buid);
        state.setSid(sid);
        state.setActive(true);
        State savedState=stateRepo.save(state);
        return StateMapper.mapToStateDto(savedState);


    }

//GET STATES.................................................

    @Override
    public List<StateDto> getStates(String zid, String cid, String rid, String buid, String eid) {

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

        List<State> states=stateRepo.findByZoneIdAndCountryIdAndRegionIdAndBussinessUniId(zid,cid,rid,buid);
        List<StateDto> stateDtos=states.stream().map(state -> StateMapper.mapToStateDto(state)).collect(Collectors.toList());
        return stateDtos;
    }


// Get statae by id...........................................................


    @Override
    public StateDto getStateById(String zid, String cid, String rid, String buid, String eid,String sid) {
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

        State state=stateRepo.findBySid(sid).
                orElseThrow(()->new RuntimeException("state not found"));

        if(!state.getZoneId().equals(zid)){
            throw new RuntimeException("state not belong to zone");
        }

        StateDto stateDto=StateMapper.mapToStateDto(state);
        return stateDto;
    }



//    ..............update.................................................

    @Override
    public StateDto updateState(StateDto stateDto, Users loggedUser, String sid, String zid, String cid, String rid, String buid, String eid) {


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

        State state=stateRepo.findBySid(sid).
                orElseThrow(()->new RuntimeException("state not found"));

        if(!state.getZoneId().equals(zid)){
            throw new RuntimeException("state not belong to zone");
        }

//        state.setName(stateDto.getName());

        if(stateDto.getName()!=null){
            state.setName(stateDto.getName());
        }
//        state.setDescription(stateDto.getDescription());

        if(stateDto.getDescription()!=null){
            state.setDescription(stateDto.getDescription());
        }


        state.setActive(stateDto.isActive());
        state.setUpdatedAt(LocalDateTime.now());
        state.setUpdatedBy(loggedUser.getUser_id());

        State updated=stateRepo.save(state);
        return StateMapper.mapToStateDto(updated);

    }

    @Override
    public List<StateDto> getAllStates() {
        List<State> states=stateRepo.findAll();
        List<StateDto> stateDtos=states.stream().map(state->StateMapper.mapToStateDto(state)).collect(Collectors.toList());
        return stateDtos;

    }

//
//    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
//    public StateDto createState(StateDto stateDto) {
//
//        BussinessUnit be=bussinessRepo.findById(stateDto.getBussinessEnterpriseId())
//                .orElseThrow(()->new RuntimeException("Bussiness not found"));
//
//        Region region=regionRepo.findById(stateDto.getRegionId())
//                .orElseThrow(()->new RuntimeException("region not found"));
//
//        Country c=countryRepo.findById(stateDto.getCountryId())
//                .orElseThrow(()->new RuntimeException("country not found"));
//
//        Zone z=zoneRepo.findById(stateDto.getZoneId())
//                .orElseThrow(()->new RuntimeException("zone not found"));
//
//        State state=StateMapper.mapToState(stateDto,z,c,region,be);  //        same order in mapper
//
//        State saved=stateRepo.save(state);
//
//        return StateMapper.mapToStateDto(saved);
//
//    }



//    @Override
//    public List<StateDto> findAll() {
//        List<State> states=stateRepo.findAll();
//        return states.stream().map(state ->StateMapper.mapToStateDto(state)).collect(Collectors.toList());
//    }
//
//    @Override
//    public StateDto getStateById(long id) {
//
//        State state=stateRepo.findById(id).
//                orElseThrow(()->new RuntimeException("state not found"));
//
//        return StateMapper.mapToStateDto(state);
//
//    }
}
