package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.BussinessUnitDto;
import com.project.Permission.of.lead.entity.BussinessUnit;

import java.time.LocalDateTime;

public class BussinessUnitMapper {

    public static BussinessUnit mapToBussinessEnterprise(BussinessUnitDto bussinessUnitDto, String createdBy, String updatedBy) {
        LocalDateTime now = LocalDateTime.now();

        return new BussinessUnit(
                bussinessUnitDto.getId(),                                   // Long id
                bussinessUnitDto.getName(),                                 // String name
                bussinessUnitDto.getDescription(),                          // String description
                bussinessUnitDto.getIndustry(),                             // String industry
                bussinessUnitDto.getLegalForumEnum(),                       // Integer legalFormEnum
                bussinessUnitDto.getAddressId(),                                       // Address
                bussinessUnitDto.getContactEmail(),
                bussinessUnitDto.getIsdCode(),                              // String contactEmail
                bussinessUnitDto.getContactPhone(),                          // String contactPhone
                bussinessUnitDto.isActive(),                                // boolean isActive
                bussinessUnitDto.getCreatedAt() != null ? bussinessUnitDto.getCreatedAt() : now,   // LocalDateTime createdAt
                createdBy,                         // createdBy
                null,                                           // LocalDateTime updatedAt
                updatedBy ,              // updatedBy
                bussinessUnitDto.getEnterpriseId(),
              null
        );
    }


    public static BussinessUnitDto mapToBussinessEnterpriseDto(BussinessUnit bussinessUnit) {

        return new BussinessUnitDto(

                bussinessUnit.getId(),
                bussinessUnit.getName(),
                bussinessUnit.getDescription(),
                bussinessUnit.getIndustry(),
                bussinessUnit.getLegalFormEnum(),
                bussinessUnit.getAddressId() != null ? bussinessUnit.getAddressId(): null,
                bussinessUnit.getContactEmail(),
                bussinessUnit.getIsdCode(),
                bussinessUnit.getContactPhone(),
                bussinessUnit.isActive(),
                bussinessUnit.getCreatedAt(),
                bussinessUnit.getCreatedBy() !=null?  bussinessUnit.getCreatedBy() : null,

                bussinessUnit.getUpdatedAt(),
                bussinessUnit.getUpdatedBy()!=null? bussinessUnit.getUpdatedBy(): null,
                bussinessUnit.getEnterpriseId(),
                bussinessUnit.getBuid()


        );

    }

}
