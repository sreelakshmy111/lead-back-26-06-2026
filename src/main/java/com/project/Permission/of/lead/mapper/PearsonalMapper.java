package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.PersonalManagementDto;
import com.project.Permission.of.lead.entity.PersonalManagement;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PearsonalMapper {

    public static PersonalManagement maptoPersonalManagement(PersonalManagementDto personalDto, Long loggedInUser, String enterpriseId) {
        LocalDateTime now=LocalDateTime.now();
        return new PersonalManagement(
                personalDto.getId(),
                personalDto.getEmpId(),
                personalDto.getName(),
                personalDto.getGender(),
                personalDto.getDob(),
                personalDto.getIsdCode(),
                personalDto.getPhone(),
                personalDto.getEmail(),
                personalDto.getQualification(),
                personalDto.getExperience(),
                enterpriseId,
                personalDto.getBuid() !=null? personalDto.getBuid():null,
                personalDto.getTerritoryId() != null
                        ? new ArrayList<>(personalDto.getTerritoryId())
                        : new ArrayList<>(),
                personalDto.isActive(),
                personalDto.getCreatedAt() !=null? personalDto.getCreatedAt():now,
                loggedInUser,
                personalDto.getUpdatedAt(),
                personalDto.getUpdatedBy(),
                personalDto.getAddressId()

        );
    }


    public static PersonalManagementDto maptoPersonalManagementDto( PersonalManagement personal) {
        return new PersonalManagementDto(
                personal.getId(),
                personal.getEmpId(),
                personal.getName(),
                personal.getGender(),
                personal.getDob(),
                personal.getIsdCode(),
                personal.getPhone(),
                personal.getEmail(),
                personal.getQualification(),
                personal.getExperience(),
                personal.getEid(),
                personal.getBuid(),
                personal.getTerritoryId(),
                personal.isActive(),
                personal.getCreated_at(),
                personal.getCreated_by(),
                personal.getUpdated_at(),
                personal.getCreated_by(),
                personal.getAddressId()
        );
    }



    public static PersonalManagementDto toDto(
            String empId,
            String enterpriseId,
            String territoryId
    ) {
        PersonalManagementDto dto = new PersonalManagementDto();

        dto.setEmpId(empId);
        dto.setEid(enterpriseId);
        dto.setTerritoryId(new ArrayList<>() {{
            add(territoryId);
        }});
        dto.setActive(true);
        dto.setCreatedAt(LocalDateTime.now());

        return dto;
    }
}
