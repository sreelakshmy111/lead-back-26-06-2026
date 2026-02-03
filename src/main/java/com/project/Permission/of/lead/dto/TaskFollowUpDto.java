package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TaskFollowUpDto {

    private Long id;

    private String bussinessUnitId;

    private String leadId;

    private LocalDateTime nextFollowUp;

    private String note;

    private LocalDateTime createdDate;

    private Long createdBy;

    private LocalDateTime updatedDate;

    private Long updatedBy;


}
