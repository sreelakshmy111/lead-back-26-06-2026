package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.RegionDto;
import com.project.Permission.of.lead.entity.Region;

import java.time.LocalDateTime;

public class RegionMapper {


    public static Region mapToRegion(RegionDto regionDto, String createdBy, String updatedBy, String bussinessUnitId) {

        LocalDateTime now = LocalDateTime.now();
        return new Region(

                regionDto.getId(),
                regionDto.getName(),
                regionDto.getDescription(),
                regionDto.isActive(),
                regionDto.getCreatedAt()!=null? regionDto.getCreatedAt():now,
                createdBy,
                null,
                updatedBy,
                bussinessUnitId,
                null

        );
    }


    public static RegionDto mapToRegionDto(Region region) {

        return new RegionDto(

                region.getId(),
                region.getName(),
                region.getDescription(),
                region.isActive(),
                region.getCreatedAt(),
                region.getCreatedBy(),
                region.getUpdatedAt(),
                region.getUpdatedBy()!=null? region.getUpdatedBy():null,
                region.getBussinessUnitId(),
                region.getRid()

        );
    }


}
