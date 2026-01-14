package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.CountryDto;
import com.project.Permission.of.lead.entity.Country;

import java.time.LocalDateTime;

public class CountryMapper {

    public static Country mapToCountry(CountryDto countryDto, Long createdBy, Long updatedBy, String regionId, String bussinesUnitId) {
        LocalDateTime now=LocalDateTime.now();
        return new Country(

                countryDto.getId(),
                countryDto.getName(),
                countryDto.getDescription(),
                countryDto.isActive(),
                countryDto.getCreatedAt()!=null? countryDto.getCreatedAt():now,
                createdBy,
                null,
                updatedBy,
                regionId,
                bussinesUnitId,
                null

        );
    }

    public static CountryDto mapToCountryDto(Country country) {

        return new CountryDto(

                country.getId(),
                country.getName(),
                country.getDescription(),
                country.isActive(),
                country.getCreatedAt(),
                country.getCreatedBy(),
                country.getUpdatedAt(),
                country.getUpdatedBy()!=null? country.getUpdatedBy():null,
                country.getRegionId(),
                country.getBussinessUnitId(),
                country.getCid()


        );


    }


}
