package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeritoryDto {

    private Long id;
    private String name;
    private String description;
    private boolean active;
    private LocalDateTime creationAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    private String districtId;
    private String stateId;
    private String zoneId;
    private String countryId;
    private String regionId;
    private String bussinessUnitId;
    private String tid;



}
