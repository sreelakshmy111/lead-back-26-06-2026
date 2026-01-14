package com.project.Permission.of.lead.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long role_id;
    private String role_name;

    private String description;

    private boolean isDisabled;

    private Boolean isSystemRole;

    private Boolean isSelfService;

    private Integer position;

    private String identifier;

    private Integer legalFormEnum;


//
}
