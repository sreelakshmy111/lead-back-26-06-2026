package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class LeadsDto {
    private Long id;
    private String lid;
    private String leadSource;
    private String leadFor;
    private String ProductGroup;
    private String ProductType;
    private String productSku;
    private String serviceGroup;
    private String serviceType;
    private String serviceItem;
    private String employeeId;
    private String territoryId;
    private String contactId;
    private String eid;
    private String buid;
    private boolean active;

    private LocalDateTime created_at;
    private Long created_by;
    private LocalDateTime updated_at;
    private Long updated_by;


}
