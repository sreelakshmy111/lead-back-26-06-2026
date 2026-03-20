package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.TeritoryDto;
import com.project.Permission.of.lead.entity.*;

import java.time.LocalDateTime;

public class TeritoryMapper {


    public static Teritory mapToTeritory(TeritoryDto teritoryDto, String createdBy, String updatedBy, String districtId, String stateId, String zoneId, String countryId, String regionId, String bussinessUnitId) {
       LocalDateTime now=LocalDateTime.now();
        return new Teritory(

                teritoryDto.getId(),
                teritoryDto.getName(),
                teritoryDto.getDescription(),
                teritoryDto.isActive(),
                teritoryDto.getCreationAt()!=null? teritoryDto.getCreationAt():now,
                createdBy,
                teritoryDto.getUpdatedAt()!=null? teritoryDto.getUpdatedAt():null,
                updatedBy,
                districtId,
                stateId,
                zoneId,
                countryId,
                regionId,
                bussinessUnitId,
                null



        );
    }

    public static TeritoryDto mapToTeritoryDto(Teritory teritory) {
        return new TeritoryDto(
                teritory.getId(),
                teritory.getName(),
                teritory.getDescription(),
                teritory.isActive(),
                teritory.getCreatedAt(),
                teritory.getCreatedBy(),
                teritory.getUpdatedAt()!=null? teritory.getUpdatedAt():null,
                teritory.getUpdatedBy(),
                teritory.getDistrictId(),
                teritory.getStateId(),
                teritory.getZoneId(),
                teritory.getCountryId(),
                teritory.getRegionId(),
                teritory.getBussinessUnitId(),
                teritory.getTid()





        );
    }



}
