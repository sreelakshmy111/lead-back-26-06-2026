package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.EmployeeDraftDto;
import com.project.Permission.of.lead.entity.EmployeeDraft;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EmployeeDraftMapper {

    public static EmployeeDraft maptoEmployeeDraft(EmployeeDraftDto employeeDraftDto) {
        LocalDateTime now=LocalDateTime.now();
        return new EmployeeDraft(
                employeeDraftDto.getId(),

                employeeDraftDto.getFirstName(),
                employeeDraftDto.getMiddleName(),
                employeeDraftDto.getLastName(),
                employeeDraftDto.getEmpId(),
                employeeDraftDto.getGender(),
               employeeDraftDto.getDob(),
                employeeDraftDto.getIsdCode(),
               employeeDraftDto.getPhone(),
                employeeDraftDto.getEmail(),
                employeeDraftDto.getQualification(),
                employeeDraftDto.getExperience(),
                employeeDraftDto.getEid()!=null? employeeDraftDto.getEid():null,
                employeeDraftDto.getBuid() !=null? employeeDraftDto.getBuid():null,
                employeeDraftDto.getTerritoryId() != null
                        ? new ArrayList<>(employeeDraftDto.getTerritoryId())
                        : new ArrayList<>(),
                employeeDraftDto.isActive(),
                employeeDraftDto.getCreatedAt() !=null? employeeDraftDto.getCreatedAt():now,
                employeeDraftDto.getCreatedBy(),
                employeeDraftDto.getUpdatedAt(),
                employeeDraftDto.getUpdatedBy(),
                employeeDraftDto.getAddressId()


        );
    }


    public static EmployeeDraftDto mapToEmployeeDRaftDto( EmployeeDraft employeeDraft) {

        EmployeeDraftDto dto = new EmployeeDraftDto();

        dto.setId(employeeDraft.getId());

        dto.setFirstName(employeeDraft.getFirstName());
        dto.setMiddleName(employeeDraft.getMiddleName());
        dto.setLastName(employeeDraft.getLastName());
        dto.setEmpId(employeeDraft.getEmpId());
        dto.setGender(employeeDraft.getGender());
        dto.setDob(employeeDraft.getDob());
        dto.setIsdCode(employeeDraft.getIsdCode());
        dto.setPhone(employeeDraft.getPhone());
        dto.setEmail(employeeDraft.getEmail());
        dto.setQualification(employeeDraft.getQualification());
        dto.setExperience(employeeDraft.getExperience());
        dto.setEid(employeeDraft.getEid());
        dto.setBuid(employeeDraft.getBuid());
        dto.setTerritoryId(employeeDraft.getTerritoryId());
        dto.setActive(employeeDraft.isActive());
        dto.setCreatedAt(employeeDraft.getCreated_at());
        dto.setCreatedBy(employeeDraft.getCreatedBy());
        dto.setUpdatedAt(employeeDraft.getUpdated_at());
        dto.setUpdatedBy(employeeDraft.getUpdated_by());
        dto.setAddressId(employeeDraft.getAddressId());

        return dto;



    }



    public static EmployeeDraftDto toDto(
            String empId,
            String enterpriseId,
            String territoryId
    ) {
        EmployeeDraftDto dto = new EmployeeDraftDto();

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
