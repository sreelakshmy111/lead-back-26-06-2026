package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseDto {
private Long id;
private String name;
private String description;
private String industry;
private Integer legalFormEnum;
private Long addressId;
private String contactEmail;
private String isdCode;
private String contactPhone;

private  boolean active;
private LocalDateTime createdAt;
private Long createdBy;
private LocalDateTime updatedAt;
private Long updatedBy;

private String eid;




}
