package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    private Long user_id;           // optional, for responses
    private String username;
    private String password;

    private String email;
    private Long addressId;
}
