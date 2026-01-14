package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.DistrictDto;
import com.project.Permission.of.lead.entity.*;

import java.time.LocalDateTime;

public class DistrictMapper {

    public static District mapToDistrict(DistrictDto districtDto, Long createdBy, Long updatedBy, String stateId, String zoneId, String countryId, String regionId, String bussinessUnitId) {
        LocalDateTime now=LocalDateTime.now();
        return new District(

                districtDto.getId(),
                districtDto.getName(),
                districtDto.getDescription(),
                districtDto.isActive(),
                districtDto.getCreatedAt()!=null? districtDto.getCreatedAt():now,
                createdBy,
                null,
                updatedBy,
                stateId,
                zoneId,
                countryId,
                regionId,
                bussinessUnitId,
                null


        );

    }

    public static DistrictDto mapToDistrictDto(District district){
        return new DistrictDto(

                district.getId(),
                district.getName(),
                district.getDescription(),
                district.isActive(),
                district.getCreatedAt(),
                district.getCreatedBy(),
                district.getUpdatedAt(),
                district.getUpdatedBy(),
                district.getStateId(),
                district.getZoneId(),
                district.getCountryId(),
                district.getRegionId(),
                district.getBussinessUnitId(),
                district.getDid()



        );
    }


}
