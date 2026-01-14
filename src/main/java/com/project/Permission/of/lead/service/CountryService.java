package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.CountryDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface CountryService {
    CountryDto createCountry(CountryDto countryDto, String eid, String buid, String rid, Users loggedInUser);

    List<CountryDto> getCoutries(String eid, String buid, String rid);

    CountryDto updateCountriesByHierarchyIds(CountryDto countryDto, String cid, String eid, String buid, String rid, Users loggedUser);

    List<CountryDto> findAll();

    CountryDto getCoutriesById(String eid, String buid, String rid, String cid);
//    CountryDto createCountry(CountryDto dto);

//    List<CountryDto> getAllCountries();

//    CountryDto getCountryById(Long id);
}
