package com.project.Permission.of.lead.service.CountryServiceImpl;

import com.project.Permission.of.lead.dto.CountryDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.CountryMapper;
import com.project.Permission.of.lead.repository.BussinessUnitRepository;
import com.project.Permission.of.lead.repository.CountryRepository;
import com.project.Permission.of.lead.repository.EnterpriseRepostory;
import com.project.Permission.of.lead.repository.RegionRepository;
import com.project.Permission.of.lead.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    @Autowired
    private EnterpriseRepostory enterpriseRepo;

    @Autowired
    private BussinessUnitRepository bussinessRepo;

    @Autowired
    private RegionRepository regionRepo;

    @Autowired
    private CountryRepository countryRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public CountryDto createCountry(CountryDto countryDto, String eid, String buid, String rid, Users loggedInUser) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("enterprise not found"));

        BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid)
                .orElseThrow(()-> new RuntimeException("bussiness unit not found"));

        if (!bussinessUnit.getEnterpriseId().equals(eid)) {
            throw new RuntimeException("Business unit does not belong to given enterprise");
        }

        Region region=regionRepo.findByRid(rid)
                .orElseThrow(()-> new RuntimeException("region not found"));

        if(!region.getBussinessUnitId().equals(buid)){
            throw new RuntimeException("region doesnot belong to given bussiness unit");
        }



        String cid=jdbcTemplate.queryForObject(
                "SELECT create_entity_id(?)",
                new Object[] {"COUNTRY"},
                String.class
        );

        System.out.println("Generated country ID: " + cid);

        Country country=CountryMapper.mapToCountry(countryDto,loggedInUser.getUser_id(),null,rid,buid);
        country.setCid(cid);
        country.setActive(true);
        Country savedcountry=countryRepo.save(country);
        return CountryMapper.mapToCountryDto(savedcountry);

    }

    // ... get all countries................................

    @Override
    public List<CountryDto> getCoutries(String eid, String buid, String rid) {

        enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("enterprise not found"));

       BussinessUnit bussinessUnit= bussinessRepo.findByBuid(buid)
                .orElseThrow(()-> new RuntimeException("bussiness unit not found"));

        regionRepo.findByRid(rid)
                .orElseThrow(()-> new RuntimeException("region not found"));

        System.out.println("Enterprise ID from Business Unit: " + bussinessUnit.getEnterpriseId());

        List<Country> countries=countryRepo.findByBussinessUnitIdAndRegionId(buid,rid);

        List<CountryDto> countryDtos=countries.stream().map(country->CountryMapper.mapToCountryDto(country)).collect(Collectors.toList());
        return countryDtos;

    }



// .............UPDATE.............................................................

    @Override
    public CountryDto updateCountriesByHierarchyIds(CountryDto countryDto, String cid, String eid, String buid, String rid, Users loggedUser) {
        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("enterprisenot found"));


        BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid).orElseThrow(
                ()-> new RuntimeException("bussiness unit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)) {
            throw new RuntimeException("bussnies unit not belong to given enterprise");
        }


        Region region=regionRepo.findByRid(rid)
                .orElseThrow(()-> new RuntimeException("region not found"));


        if(!region.getBussinessUnitId().equals(buid)) {
            throw new RuntimeException("region don't belong to given bussiness unit");
        }

        Country country=countryRepo.findByCid(cid).
                orElseThrow(()-> new RuntimeException("country not found"));

        if(!country.getRegionId().equals(rid)) {
            throw new RuntimeException("Country don't belong to given region");

        }


        System.out.println("EnterpriseId=" + eid +
                ", BUId=" + buid +
                ", RegionId=" + rid +
                ", CountryId=" + cid);


//        country.setName(countryDto.getName());

        if(countryDto.getName()!=null){
            country.setName(countryDto.getName());
        }

        if(countryDto.getDescription()!=null){
            country.setDescription(countryDto.getDescription());
        }
//        country.setDescription(countryDto.getDescription());
        country.setActive(countryDto.isActive());
        country.setUpdatedAt(LocalDateTime.now());
        country.setUpdatedBy(loggedUser.getUser_id());

        Country updatedcountry=countryRepo.save(country);
        return CountryMapper.mapToCountryDto(updatedcountry);


    }

//    ...............get all countries.............................................

    @Override

    public List<CountryDto> findAll() {
        List<Country> countries=countryRepo.findAll();
        List<CountryDto> countryDtos=countries.stream().map(c->CountryMapper.mapToCountryDto(c)).collect(Collectors.toList());
        return countryDtos;
    }

    //....get countries by cid.........................................................
    @Override
    public CountryDto getCoutriesById(String eid, String buid, String rid, String cid) {

        Enterprise enterprise=enterpriseRepo.findByEid(eid)
                .orElseThrow(()-> new RuntimeException("enterprisenot found"));


        BussinessUnit bussinessUnit=bussinessRepo.findByBuid(buid).orElseThrow(
                ()-> new RuntimeException("bussiness unit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)) {
            throw new RuntimeException("bussnies unit not belong to given enterprise");
        }


        Region region=regionRepo.findByRid(rid)
                .orElseThrow(()-> new RuntimeException("region not found"));


        if(!region.getBussinessUnitId().equals(buid)) {
            throw new RuntimeException("region don't belong to given bussiness unit");
        }

        Country country=countryRepo.findByCid(cid).
                orElseThrow(()-> new RuntimeException("country not found"));

        if(!country.getRegionId().equals(rid)) {
            throw new RuntimeException("Country don't belong to given region");

        }

        CountryDto countryDto=CountryMapper.mapToCountryDto(country);
        return countryDto;


    }


//    @Override
//
//    @PreAuthorize("hasRole('BUSINESS_ADMIN')")
//    public CountryDto createCountry(CountryDto countryDto) {
//
//        BussinessUnit be = bussinessRepo.findById(countryDto.getBussinessEnterpriseId()).
//                orElseThrow(()-> new RuntimeException("Bussiness not found"));
//
//        Region re=regionRepo.findById(countryDto.getRegionId()).
//                orElseThrow(()->new RuntimeException("Region not found"));
//
//
//
//
//        Country country= CountryMapper.mapToCountry(countryDto, re, be);
//
//        Country saved=countryRepo.save(country);
//        return CountryMapper.mapToCountryDto(saved);
//
//
//    }
//
//    @Override
//    public List<CountryDto> getAllCountries() {
//        List<Country> countries=countryRepo.findAll();
//        return countries.stream().map(country -> CountryMapper.mapToCountryDto(country)).collect(Collectors.toList());
//    }
//
//    @Override
//    public CountryDto getCountryById(Long id) {
//
//        Country country=countryRepo.findById(id).
//                orElseThrow(()->new RuntimeException("Country not found"));
//
//        return CountryMapper.mapToCountryDto(country);
//
//    }
}
