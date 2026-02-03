package com.project.Permission.of.lead.mapper;


import com.project.Permission.of.lead.dto.LeadStatusCustomDto;
import com.project.Permission.of.lead.entity.LeadStatusCustom;
import com.project.Permission.of.lead.entity.Users;

import java.time.LocalDateTime;

public class LeadStatusMapper {


    public static LeadStatusCustom mapToLeadStatusCustom(String eid, String buid, Users loggedInUser, LeadStatusCustomDto leadStatusCustomDto){
        LocalDateTime now=LocalDateTime.now();
        return new LeadStatusCustom(
                leadStatusCustomDto.getId(),
                eid,
                buid,
                leadStatusCustomDto.getStatusCode(),
                leadStatusCustomDto.getStatusDescription(),
                leadStatusCustomDto.getStatusSequence(),
                now,
                leadStatusCustomDto.getCreatedBy(),
                leadStatusCustomDto.getUpdatedAt(),
                leadStatusCustomDto.getUpdatedBy()


                );
    }


    public static LeadStatusCustomDto mapToLeadStatusCustomDto(LeadStatusCustom leadStatusCustom){
        return new LeadStatusCustomDto(
                leadStatusCustom.getId(),
                leadStatusCustom.getEnterpriseId(),
                leadStatusCustom.getBussinessUnitId(),
                leadStatusCustom.getStatusCode(),
                leadStatusCustom.getStatusDescription(),
                leadStatusCustom.getStatusSequence(),
                leadStatusCustom.getCreatedAt(),
                leadStatusCustom.getCreatedBy(),
                leadStatusCustom.getUpdatedAt(),
                leadStatusCustom.getUpdatedBy()

        );
    }

}
