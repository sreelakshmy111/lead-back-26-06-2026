package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LeadStatusCustomDto {

    private Long id;
    private String enterpriseId;
    private String bussinessUnitId;
    private String statusCode;
    private String statusDescription;
    private Long statusSequence;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

}
