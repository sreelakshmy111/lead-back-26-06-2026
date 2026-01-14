package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.ZoneDto;
import com.project.Permission.of.lead.entity.Zone;

import java.time.LocalDateTime;

public class ZoneMapper {


    public static Zone mapToZone(ZoneDto zoneDto, Long createdBy, Long updatedBy, String countryId, String regionId, String bussinessunitId) {
        LocalDateTime now=LocalDateTime.now();
        return new Zone(

                zoneDto.getId(),
                zoneDto.getName(),
                zoneDto.getDescription(),
                zoneDto.isActive(),

                zoneDto.getUpdatedAt()!=null? zoneDto.getUpdatedAt():now,
                createdBy,
                null,
                updatedBy,

                countryId,
                regionId,
                bussinessunitId,
                null

        );
    }



    public static ZoneDto mapToZoneDto(Zone zone){

        return new ZoneDto(

                zone.getId(),
                zone.getName(),
                zone.getDescription(),
                zone.isActive(),


                zone.getCreatedAt(),
                zone.getCreatedBy(),
                zone.getUpdatedAt(),
                zone.getUpdatedBy()!=null? zone.getUpdatedBy():null,
                zone.getRegionId(),
                zone.getCountryId(),
                zone.getBussinessUnitId(),
                zone.getZid()


        );

    }

}
