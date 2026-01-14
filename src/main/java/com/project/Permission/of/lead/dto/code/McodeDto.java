package com.project.Permission.of.lead.dto.code;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class McodeDto {

    private Long id;
    private String codeName;


    @JsonProperty("is_system_default")
    private boolean systemDefault;

    // Optional: list of child value IDs
    private Long parentId;

    private List<McodeValueDto> codeValues;


}
