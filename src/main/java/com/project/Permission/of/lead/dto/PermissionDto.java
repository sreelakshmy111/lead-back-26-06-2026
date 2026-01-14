package com.project.Permission.of.lead.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PermissionDto {


    private Long permissionId;                // For updates, backend maps to permission_id
    private String actionName;      // Action name like READ, CREATE, UPDATE
    private String code;            // Unique code for permission
    private String entityName;      // Entity the permission applies to
    private String grouping;        // Logical grouping like "configuration"
    private boolean canMakerChecker; // Indicates Maker-Checker workflow
    private String description;      // Human-readable description
}
