package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.EnterpriseDto;
import com.project.Permission.of.lead.entity.Address;
import com.project.Permission.of.lead.entity.Enterprise;
import com.project.Permission.of.lead.entity.Users;

import java.time.LocalDateTime;

public class EnterpriseMapper {


    public static Enterprise mapToEnterprise(EnterpriseDto enterpriseDto, Long createdById, Long updatedById) {
        LocalDateTime now = LocalDateTime.now();
        return new Enterprise(
                enterpriseDto.getId(),
                enterpriseDto.getName(),
                enterpriseDto.getDescription(),
                enterpriseDto.getIndustry(),
                enterpriseDto.getLegalFormEnum(),
                enterpriseDto.getAddressId(),
                enterpriseDto.getContactEmail(),
                enterpriseDto.getIsdCode(),
                enterpriseDto.getContactPhone(),
                enterpriseDto.isActive(),
                enterpriseDto.getCreatedAt()!=null? LocalDateTime.now():null,
                createdById,
                null,
                updatedById ,   //its set nullll initailly
                null

        );


    }


    public static  EnterpriseDto mapToEnterpriseDto(Enterprise enterprise) {
        return new EnterpriseDto(

                enterprise.getId(),
                enterprise.getName(),
                enterprise.getDescription(),
                enterprise.getIndustry(),
                enterprise.getLegalFormEnum(),
                enterprise.getAddressId(),
                enterprise.getContactEmail(),
                enterprise.getIsdCode(),
                enterprise.getContactPhone(),
                enterprise.isActive(),
                enterprise.getCreatedAt(),
                enterprise.getCreatedBy() != null ? enterprise.getCreatedBy(): null,
                enterprise.getUpdatedAt(),
                enterprise.getUpdatedBy() != null ? enterprise.getUpdatedBy() : null,
                enterprise.getEid()

        );

    }



}
