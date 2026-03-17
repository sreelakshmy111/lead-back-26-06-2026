package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDto {

    private String token;
    private UserDto userDto;
    private Map<String, Object> tower;




}
