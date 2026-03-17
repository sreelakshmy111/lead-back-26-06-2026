package com.project.Permission.of.lead.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterResponseDto {

    private Long empdId;
    private String tower;
    private String token;
}
