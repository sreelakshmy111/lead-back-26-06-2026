package com.project.Permission.of.lead.dto.code;

import com.project.Permission.of.lead.entity.code.Mcode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class McodeValueDto {

    private Long id;
    private boolean isActive;
    private String codeDescription;
    private String codeValue;
    private boolean isMandatory;
    private int orderPosition;
    private String storeValue;
    private Long parentId;
    private Long codeId;


    public McodeValueDto(Long id, boolean isActive, String codeDescription, String codeValue, boolean isMandatory, int orderPosition, String storeValue, Long parentId, Long codeId) {
        this.id = id;
        this.isActive = isActive;
        this.codeDescription = codeDescription;
        this.codeValue = codeValue;
        this.isMandatory = isMandatory;
        this.orderPosition = orderPosition;
        this.storeValue = storeValue;
        this.parentId = parentId;
        this.codeId = codeId;
    }
}
