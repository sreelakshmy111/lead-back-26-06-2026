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

    private Long roleId;
    
    private String roleName;

    private String description;

    private boolean isDisabled;

    private Boolean isSystemRole;

    private Boolean isSelfService;

    private Integer position;

    private String identifier;

    private Integer legalFormEnum;

    public RoleDto(Long roleId, String roleName) {
    }


//
}
