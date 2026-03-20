package com.project.Permission.of.lead.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BussinessUnitDto {

    private Long id;
    private String name;
    private String description;
    private String industry;
    private Integer legalForumEnum;
    private Long addressId;
    private String contactEmail;
    private String isdCode;
    private String contactPhone;
    private boolean active;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private String enterpriseId;
    private String buid;




}
